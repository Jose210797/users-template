package com.template.users.modules.roles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.template.users.modules.roles.dto.request.CreateRoleDto;
import com.template.users.modules.roles.dto.response.RoleCreatedMapped;
import com.template.users.modules.roles.services.RoleService;
import com.template.users.shared.responses.CustomResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public CustomResponse<RoleCreatedMapped> createRole(@Valid @RequestBody CreateRoleDto createRoleDto) {
        RoleCreatedMapped createdRole = roleService.createRole(createRoleDto);
        return new CustomResponse<RoleCreatedMapped>(
                "Rol creado exitosamente",
                createdRole,
                HttpStatus.CREATED.value());
    }
}