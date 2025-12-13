package com.template.users.modules.users.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.template.users.modules.users.entity.UserEntity;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    public Long countByUsername(String username);
    public Optional<UserEntity> findByIdUserAndActive(Long idUser, Boolean active);
    public Optional<UserEntity> findByUsernameAndActive(String username, Boolean active);

    @Query("select u from UserEntity u left join fetch u.roles")
    public List<UserEntity> findAll();
}
