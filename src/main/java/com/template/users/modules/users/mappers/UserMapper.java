package com.template.users.modules.users.mappers;

import org.springframework.stereotype.Component;

import com.template.users.modules.roles.dto.response.RoleMapped;
import com.template.users.modules.users.dto.response.UserCreatedMapped;
import com.template.users.modules.users.dto.response.UserMapped;
import com.template.users.modules.users.dto.response.UserUpdatedMapped;
import com.template.users.modules.users.entity.UserEntity;

@Component
public class UserMapper {

    public UserCreatedMapped toUserCreatedMapped(Long idUser) {
        return new UserCreatedMapped(idUser);
    }

    public UserUpdatedMapped toUserUpdatedMapped(UserEntity userEntity) {
        return new UserUpdatedMapped(
                userEntity.getIdUser(),
                userEntity.getUsername(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt());
    }

    public UserMapped toUserMapped(UserEntity userEntity) {
        return new UserMapped(
                userEntity.getIdUser(),
                userEntity.getUsername(),
                userEntity.getCreatedAt(),
                userEntity.getUpdatedAt(),
                userEntity.getActive(),
                userEntity.getRoles().stream().map(role -> new RoleMapped(
                        role.getIdRole(),
                        role.getName(),
                        role.getCreatedAt(),
                        role.getUpdatedAt())).toList());
    }
}
