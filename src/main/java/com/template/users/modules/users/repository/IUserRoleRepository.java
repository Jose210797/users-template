package com.template.users.modules.users.repository;

import org.springframework.stereotype.Repository;

import com.template.users.modules.users.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface IUserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

}
