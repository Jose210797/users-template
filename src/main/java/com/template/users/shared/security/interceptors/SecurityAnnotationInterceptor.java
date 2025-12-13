package com.template.users.shared.security.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.users.shared.responses.CustomResponse;
import com.template.users.shared.security.annotations.Permissions;
import com.template.users.shared.security.annotations.Public;
import com.template.users.shared.security.dto.UserAuthInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class SecurityAnnotationInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;

    public SecurityAnnotationInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        if (isPublicEndpoint(handlerMethod)) {
            return true;
        }


        UserAuthInfo userAuthInfo = (UserAuthInfo) request.getAttribute("userAuthInfo");
        if (userAuthInfo == null) {
            sendUnauthorizedResponse(response, "Token de acceso requerido para acceder a este recurso");
            return false;
        }


        Permissions permissions = getPermissionsAnnotation(handlerMethod);
        if (permissions != null) {
            if (!hasRequiredPermissions(userAuthInfo, permissions)) {
                sendForbiddenResponse(response, "No tienes los permisos necesarios para acceder a este recurso");
                return false;
            }
        }

        return true;
    }

    private boolean isPublicEndpoint(HandlerMethod handlerMethod) {
        Public methodAnnotation = handlerMethod.getMethodAnnotation(Public.class);
        
        if (methodAnnotation != null) {
            return true;
        }

        boolean classHasPublic = handlerMethod.getBeanType().isAnnotationPresent(Public.class);
        
        return classHasPublic;
    }

    private Permissions getPermissionsAnnotation(HandlerMethod handlerMethod) {

        Permissions methodAnnotation = handlerMethod.getMethodAnnotation(Permissions.class);
        if (methodAnnotation != null) {
            return methodAnnotation;
        }

        return handlerMethod.getBeanType().getAnnotation(Permissions.class);
    }

    private boolean hasRequiredPermissions(UserAuthInfo userAuthInfo, Permissions permissions) {
        List<Long> userActions = userAuthInfo.getActions();
        if (userActions == null) {
            return false;
        }

        if (permissions.anyOf().length > 0) {
            List<Long> requiredAnyActions = Arrays.stream(permissions.anyOf()).boxed().toList();
            boolean hasAnyPermission = requiredAnyActions.stream().anyMatch(userActions::contains);
            if (!hasAnyPermission) {
                return false;
            }
        }

        if (permissions.allOf().length > 0) {
            List<Long> requiredAllActions = Arrays.stream(permissions.allOf()).boxed().toList();
            boolean hasAllPermissions = userActions.containsAll(requiredAllActions);
            if (!hasAllPermissions) {
                return false;
            }
        }

        return true;
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        CustomResponse<Object> errorResponse = new CustomResponse<>(message, null, HttpStatus.UNAUTHORIZED.value());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private void sendForbiddenResponse(HttpServletResponse response, String message) throws IOException {
        CustomResponse<Object> errorResponse = new CustomResponse<>(message, null, HttpStatus.FORBIDDEN.value());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}