package com.template.users.modules.roles.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.template.users.modules.roles.dto.request.CreateRoleDto;
import com.template.users.modules.roles.dto.response.RoleCreatedMapped;
import com.template.users.modules.roles.entity.ActionEntity;
import com.template.users.modules.roles.entity.RoleAction;
import com.template.users.modules.roles.entity.RoleEntity;
import com.template.users.modules.roles.mappers.RoleMapper;
import com.template.users.modules.roles.repository.IActionRepository;
import com.template.users.modules.roles.repository.IRoleActionRepository;
import com.template.users.modules.roles.repository.IRoleRepository;

@Service
public class RoleService {
    private final IRoleRepository roleRepository;
    private final IActionRepository actionRepository;
    private final IRoleActionRepository roleActionRepository;
    private final RoleValidationService roleValidation;
    private final RoleMapper roleMapper = new RoleMapper();

    public RoleService(
        IRoleRepository roleRepository, IActionRepository actionRepository, 
        IRoleActionRepository roleActionRepository, RoleValidationService roleValidation
    ) {
        this.roleRepository = roleRepository;
        this.actionRepository = actionRepository;
        this.roleActionRepository = roleActionRepository;
        this.roleValidation = roleValidation;
    }

    @Transactional
    public RoleCreatedMapped createRole(CreateRoleDto createRoleDto) {
        this.roleValidation.validateExistNameRole(createRoleDto.getName());
        
        this.roleValidation.validateActionsExist(createRoleDto.getActionIds());

        RoleEntity role = new RoleEntity();
        role.setName(createRoleDto.getName());
        role.setActive(true);
        RoleEntity roleCreated = roleRepository.save(role);

        List<ActionEntity> actions = actionRepository.findAllById(createRoleDto.getActionIds());

        for (ActionEntity action : actions) {
            RoleAction roleAction = new RoleAction();
            roleAction.setRole(roleCreated);
            roleAction.setAction(action);
            roleAction.setActive(true);
            roleActionRepository.save(roleAction);
        }

        return roleMapper.toRoleCreatedMapped(roleCreated.getIdRole());
    }
}