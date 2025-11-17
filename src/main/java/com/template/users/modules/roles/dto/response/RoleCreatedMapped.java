package com.template.users.modules.roles.dto.response;

public class RoleCreatedMapped {
    private Long idRole;

    public RoleCreatedMapped(Long idRole) {
        this.idRole = idRole;
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }
}