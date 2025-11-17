package com.template.users.modules.roles.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.template.users.modules.roles.entity.RoleAction;

public interface IRoleActionRepository extends JpaRepository<RoleAction, Long> {
    
    List<RoleAction> findByRoleIdRoleAndActiveTrue(Long idRole);
}