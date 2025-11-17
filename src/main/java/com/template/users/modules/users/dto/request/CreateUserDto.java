package com.template.users.modules.users.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserDto {

    @NotNull
    @NotBlank
    @Size(min = 5, max = 50, message = "El usuario debe tener entre 5 y 50 caracteres")
    private String username;


    @NotNull
    @NotBlank
    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
