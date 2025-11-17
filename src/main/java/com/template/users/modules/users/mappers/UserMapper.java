package com.template.users.modules.users.mappers;


import com.template.users.modules.users.dto.response.UserCreatedMapped;
import com.template.users.modules.users.dto.response.UserUpdatedMapped;
import com.template.users.modules.users.entity.UserEntity;

public class UserMapper {
    public UserCreatedMapped toUserCreatedMapped(Long idUser) {
        return new UserCreatedMapped(idUser);
    }

    public UserUpdatedMapped toUserUpdatedMapped(UserEntity userEntity) {
        return new UserUpdatedMapped(userEntity.getIdUser(), userEntity.getUsername(), userEntity.getCreatedAt(), userEntity.getUpdatedAt());
    }
}
