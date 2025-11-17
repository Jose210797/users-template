package com.template.users.modules.roles.mappers;

import com.template.users.modules.roles.dto.response.RoleCreatedMapped;

public class RoleMapper {
    public RoleCreatedMapped toRoleCreatedMapped(Long idRole) {
        return new RoleCreatedMapped(idRole);
    }
}



