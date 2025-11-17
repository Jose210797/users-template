package com.template.users.modules.roles.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.template.users.modules.roles.entity.ActionEntity;

public interface IActionRepository extends JpaRepository<ActionEntity, Long> {
    
}