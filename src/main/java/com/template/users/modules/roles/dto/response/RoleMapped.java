package com.template.users.modules.roles.dto.response;

import java.time.ZonedDateTime;

public class RoleMapped {
    private Long idRole;
    private String name;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public RoleMapped() {
    }

    public RoleMapped(Long idRole, String name, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.idRole = idRole;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}
