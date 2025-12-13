package com.template.users.modules.roles.mappers;

import org.springframework.stereotype.Component;

import com.template.users.modules.roles.dto.response.RoleCreatedMapped;
import com.template.users.modules.roles.dto.response.RoleMapped;
import com.template.users.modules.roles.entity.RoleEntity;


@Component
public class RoleMapper {
    public RoleCreatedMapped toRoleCreatedMapped(Long idRole) {
        return new RoleCreatedMapped(idRole);
    }

    public RoleMapped toRoleMapped(RoleEntity role){
        return new RoleMapped(
            role.getIdRole(),
            role.getName(),
            role.getCreatedAt(),
            role.getUpdatedAt()
        );
    }
}



