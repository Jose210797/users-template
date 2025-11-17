package com.template.users.modules.users.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.template.users.modules.users.entity.UserEntity;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    public Long countByUsername(String username);
    public Optional<UserEntity> findByIdUserAndActive(Long idUser, Boolean active);
}
