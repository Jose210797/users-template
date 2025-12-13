package com.template.users.modules.users.dto.response;

import java.time.ZonedDateTime;
import java.util.List;

import com.template.users.modules.roles.dto.response.RoleMapped;

public class UserMapped {
    private Long idUser;
    private String username;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Boolean active;
    List<RoleMapped> roles;

    public UserMapped() {
    }

    public UserMapped(
            Long idUser,
            String username,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt,
            Boolean active,
            List<RoleMapped> roles) {
        this.idUser = idUser;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
        this.roles = roles;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<RoleMapped> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleMapped> roles) {
        this.roles = roles;
    }

}
