package com.template.users.modules.roles.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateRoleDto {
    @NotNull
    @NotEmpty
    @Size(min = 5, max = 50, message = "El nombre del rol debe tener entre 5 y 50 caracteres")
    private String name;


    @NotNull
    @Size(min = 1, message = "Debe asignar al menos una acción al rol")
    private List<Long> actionIds;


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public List<Long> getActionIds() {
        return actionIds;
    }


    public void setActionIds(List<Long> actionIds) {
        this.actionIds = actionIds;
    }

    
    
}
