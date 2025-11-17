package com.template.users.modules.roles.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.template.users.modules.roles.entity.RoleEntity;

public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {
    
    Long countByNameAndActiveTrue(String name);
    
    Optional<RoleEntity> findByIdRoleAndActiveTrue(Long idRole);
}
