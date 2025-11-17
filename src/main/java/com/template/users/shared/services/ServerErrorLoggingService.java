package com.template.users.shared.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.users.utils.ErrorIdGenerator;

import jakarta.servlet.http.HttpServletRequest;


@Service
public class ServerErrorLoggingService {
    
    private static final Logger SERVER_ERROR_LOGGER = LoggerFactory.getLogger("SERVER_ERROR_LOGGER");
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    
    private final ErrorIdGenerator errorIdGenerator;
    
    public ServerErrorLoggingService(ErrorIdGenerator errorIdGenerator) {
        this.errorIdGenerator = errorIdGenerator;
    }
    

    public String logServerError(Exception exception, HttpServletRequest request) {
        String errorId = errorIdGenerator.generateErrorId();
        
        try {
            ErrorLogEntry logEntry = createErrorLogEntry(errorId, exception, request);
            String logMessage = formatLogMessage(logEntry);
            
            SERVER_ERROR_LOGGER.error("SERVER_ERROR: {}", logMessage);
            
        } catch (Exception loggingException) {
            SERVER_ERROR_LOGGER.error("ERROR_LOGGING_FAILED - ErrorId: {} - Original: {} - Logging Error: {}", 
                errorId, exception.getMessage(), loggingException.getMessage());
        }
        
        return errorId;
    }
    
    private ErrorLogEntry createErrorLogEntry(String errorId, Exception exception, HttpServletRequest request) {
        ErrorLogEntry entry = new ErrorLogEntry();
        entry.errorId = errorId;
        entry.timestamp = LocalDateTime.now().format(ISO_FORMATTER);
        entry.exceptionType = exception.getClass().getSimpleName();
        entry.message = exception.getMessage();
        entry.stackTrace = getStackTraceAsString(exception);
        
        if (request != null) {
            entry.httpMethod = request.getMethod();
            entry.requestUri = request.getRequestURI();
            entry.queryString = request.getQueryString();
            entry.remoteAddr = getClientIpAddress(request);
            entry.userAgent = request.getHeader("User-Agent");
            entry.headers = extractHeaders(request);
            entry.requestParameters = extractRequestParameters(request);
        }
        
        return entry;
    }
    

    private String formatLogMessage(ErrorLogEntry logEntry) {
        try {
            return objectMapper.writeValueAsString(logEntry);
        } catch (JsonProcessingException e) {

            return String.format("ErrorId: %s, Exception: %s, Message: %s", 
                logEntry.errorId, logEntry.exceptionType, logEntry.message);
        }
    }
    
    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }
    
    private Map<String, String> extractHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (!isSensitiveHeader(headerName)) {
                headers.put(headerName, request.getHeader(headerName));
            }
        }
        
        return headers;
    }
    
    private Map<String, String[]> extractRequestParameters(HttpServletRequest request) {
        return new HashMap<>(request.getParameterMap());
    }
    
    private boolean isSensitiveHeader(String headerName) {
        String lowerCaseHeader = headerName.toLowerCase();
        return lowerCaseHeader.contains("authorization") || 
               lowerCaseHeader.contains("cookie") ||
               lowerCaseHeader.contains("password") ||
               lowerCaseHeader.contains("token");
    }
    
    private String getStackTraceAsString(Exception exception) {
        return Arrays.toString(exception.getStackTrace());
    }
    
    @SuppressWarnings("unused")
    private static class ErrorLogEntry {
        public String errorId;
        public String timestamp;
        public String exceptionType;
        public String message;
        public String stackTrace;
        public String httpMethod;
        public String requestUri;
        public String queryString;
        public String remoteAddr;
        public String userAgent;
        public Map<String, String> headers;
        public Map<String, String[]> requestParameters;
    }
}