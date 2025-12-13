package com.template.users.modules.auth.controllers;

import com.template.users.modules.auth.dto.request.LoginDto;
import com.template.users.modules.auth.dto.response.AuthResponseMapped;
import com.template.users.modules.auth.services.AuthService;
import com.template.users.shared.responses.CustomResponse;
import com.template.users.shared.security.annotations.Public;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Public
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public CustomResponse<AuthResponseMapped> login(@Valid @RequestBody LoginDto loginDto) {
        AuthResponseMapped authResponse = authService.login(loginDto);
        return new CustomResponse<>(
                "Acceso correcto",
                authResponse,
                HttpStatus.OK.value());
    }

    @PostMapping("/refresh")
    public CustomResponse<AuthResponseMapped> refreshToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Se requiere actualizar el token de autorización");
        }

        String refreshToken = authHeader.substring(7);
        AuthResponseMapped authResponse = authService.refreshToken(refreshToken);

        return new CustomResponse<>(
                "Token actualizado exitosamente",
                authResponse,
                HttpStatus.OK.value());
    }
}