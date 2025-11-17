package com.template.users.shared.handlers;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.template.users.shared.responses.CustomResponse;
import com.template.users.shared.services.ServerErrorLoggingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalHandlerErrors {

    private final ServerErrorLoggingService serverErrorLoggingService;

    public GlobalHandlerErrors(ServerErrorLoggingService serverErrorLoggingService) {
        this.serverErrorLoggingService = serverErrorLoggingService;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CustomResponse<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        HashMap<String, String> errorMap = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));

        return new CustomResponse<>("Ocurrió un error en los argumentos", errorMap, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public CustomResponse<Object> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        HashMap<String, String> errorMap = new HashMap<>();
        ex.getConstraintViolations()
                .stream()
                .forEach(violation -> errorMap.put(violation.getPropertyPath().toString(), violation.getMessage()));

        return new CustomResponse<>("Ocurrió un error de validación", errorMap, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public CustomResponse<Object> handleHttpMessageNotReadableExceptions(HttpMessageNotReadableException ex) {
        return new CustomResponse<>("Ocurrió un error de lectura de la respuesta", List.of(ex.getMessage()),
                HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public CustomResponse<Object> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
        HashMap<String, String> errorMap = new HashMap<>();
        errorMap.put(ex.getCause() != null ? ex.getCause().getMessage() : "Error", ex.getMessage());
        return new CustomResponse<>("Error de validación", errorMap,
                HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public CustomResponse<Object> handleResourceExceptions(Exception ex, HttpServletRequest request) {
        
        String resourceMessage = "El recurso que intenta acceder no está disponible o no existe.";
        
        return new CustomResponse<>(resourceMessage, null, HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(Exception.class)
    public CustomResponse<Object> handleGenericExceptions(Exception ex) {
        HttpServletRequest request = getCurrentHttpRequest();
        String errorId = serverErrorLoggingService.logServerError(ex, request);
        
        String userMessage = String.format(
            "Ocurrió un error inesperado. Por favor contacte al administrador. Código de error: %s", 
            errorId
        );
        
        return new CustomResponse<>(userMessage, null, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    
    //test
    private HttpServletRequest getCurrentHttpRequest() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            return attributes.getRequest();
        } catch (IllegalStateException e) {
            return null;
        }
    }

}
