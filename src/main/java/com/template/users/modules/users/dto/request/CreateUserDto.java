package com.template.users.modules.users.dto.request;

import java.util.List;

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

    @NotNull
    @Size(min = 1, message = "Debe asignar al menos un rol al usuario")
    private List<Long> roles;

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

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

}
