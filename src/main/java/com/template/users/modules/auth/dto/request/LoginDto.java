package com.template.users.modules.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginDto {
    
    @NotNull
    @NotBlank(message = "El nombre de usuario es requerido")
    private String username;
    
    @NotNull
    @NotBlank(message = "La contraseña es requerida")
    private String password;
    
    public LoginDto() {}
    
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username != null ? username.trim() : null;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}