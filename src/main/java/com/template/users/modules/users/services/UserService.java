package com.template.users.modules.users.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.template.users.modules.users.dto.request.CreateUserDto;
import com.template.users.modules.users.dto.request.UpdateUserDto;
import com.template.users.modules.users.dto.response.UserCreatedMapped;
import com.template.users.modules.users.dto.response.UserUpdatedMapped;
import com.template.users.modules.users.entity.UserEntity;
import com.template.users.modules.users.mappers.UserMapper;
import com.template.users.modules.users.repository.IUserRepository;
import com.template.users.shared.security.PasswordEncoder;


@Service
public class UserService {
    private final IUserRepository userRepository;
    private final UserValidationService userValidation;
    private final UserMapper userMapper = new UserMapper();

    public UserService(IUserRepository userRepository, UserValidationService userValidation) {
        this.userRepository = userRepository;
        this.userValidation = userValidation;
    }

    @Transactional
    public UserCreatedMapped createUser(CreateUserDto createUserDto) {
        this.userValidation.validateExistNameUser(createUserDto.getUsername());
        UserEntity user = new UserEntity();
        user.setUsername(createUserDto.getUsername());
        user.setPassword(PasswordEncoder.encode(createUserDto.getPassword()));
        UserEntity userCreated = userRepository.save(user);
        return userMapper.toUserCreatedMapped(userCreated.getIdUser());
    }

    @Transactional
    public UserUpdatedMapped updateUser(UpdateUserDto updateUserDto) {
        UserEntity user = this.userValidation.validateExistUserById(updateUserDto.getIdUser());
        if(updateUserDto.getUsername() != null && !updateUserDto.getUsername().isEmpty() && !updateUserDto.getUsername().equals(user.getUsername())) {
            this.userValidation.validateExistNameUser(updateUserDto.getUsername());
            user.setUsername(updateUserDto.getUsername());
        }
        if(updateUserDto.getPassword() != null && !updateUserDto.getPassword().isEmpty()) {
            user.setPassword(PasswordEncoder.encode(updateUserDto.getPassword()));
        }
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.toUserUpdatedMapped(updatedUser);
    }

    @Transactional
    public void deleteUser(Long idUser) {
        UserEntity user = this.userValidation.validateExistUserById(idUser);
        user.setActive(false);
        userRepository.save(user);
    }

}
