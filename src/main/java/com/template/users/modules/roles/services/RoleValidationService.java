package com.template.users.modules.roles.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.template.users.modules.roles.entity.ActionEntity;
import com.template.users.modules.roles.entity.RoleEntity;
import com.template.users.modules.roles.repository.IActionRepository;
import com.template.users.modules.roles.repository.IRoleRepository;

@Service
public class RoleValidationService {
    private final IRoleRepository roleRepository;
    private final IActionRepository actionRepository;

    public RoleValidationService(IRoleRepository roleRepository, IActionRepository actionRepository) {
        this.roleRepository = roleRepository;
        this.actionRepository = actionRepository;
    }

    public void validateExistNameRole(String name) {
        Long roleNameCount = roleRepository.countByNameAndActiveTrue(name);
        if (roleNameCount > 0) {
            throw new IllegalArgumentException("El nombre del rol no está disponible", new Throwable("name"));
        }
    }

    public RoleEntity validateExistRoleById(Long idRole) {
        RoleEntity role = this.roleRepository.findByIdRoleAndActiveTrue(idRole)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado", new Throwable("idRole")));
        return role;
    }

    public void validateActionsExist(List<Long> actionIds) {
        if (actionIds == null || actionIds.isEmpty()) {
            throw new IllegalArgumentException("Debe asignar al menos una acción al rol", new Throwable("actionIds"));
        }

        List<ActionEntity> existingActions = actionRepository.findAllById(actionIds);
        if (existingActions.size() != actionIds.size()) {
            throw new IllegalArgumentException("Una o más acciones no existen", new Throwable("actionIds"));
        }
    }
}