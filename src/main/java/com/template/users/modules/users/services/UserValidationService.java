package com.template.users.modules.users.services;

import org.springframework.stereotype.Service;

import com.template.users.modules.users.entity.UserEntity;
import com.template.users.modules.users.repository.IUserRepository;

@Service
public class UserValidationService {
    private final IUserRepository userRepository;

    public UserValidationService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateExistNameUser(String name) {
        Long usernameCount = userRepository.countByUsername(name);
        if (usernameCount > 0) {
            throw new IllegalArgumentException("El nombre de usuario no está disponible", new Throwable("username"));
        }
    }

    public UserEntity validateExistUserById(Long idUser) {
        UserEntity user = this.userRepository.findByIdUserAndActive(idUser, true)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado", new Throwable("idUser")));
        return user;
    }

}
