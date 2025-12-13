package com.template.users.modules.users.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.template.users.modules.users.dto.request.CreateUserDto;
import com.template.users.modules.users.dto.request.UpdateUserDto;
import com.template.users.modules.users.dto.response.UserCreatedMapped;
import com.template.users.modules.users.dto.response.UserMapped;
import com.template.users.modules.users.dto.response.UserUpdatedMapped;
import com.template.users.modules.users.services.UserService;
import com.template.users.shared.responses.CustomResponse;
import com.template.users.shared.security.annotations.AuthenticatedUser;
import com.template.users.shared.security.annotations.Public;
import com.template.users.shared.security.dto.UserAuthInfo;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public CustomResponse<UserCreatedMapped> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        UserCreatedMapped createdUser = userService.createUser(createUserDto);
        return new CustomResponse<UserCreatedMapped>(
                "Usuario creado exitosamente",
                createdUser,
                HttpStatus.CREATED.value());
    }

    @PutMapping
    public CustomResponse<UserUpdatedMapped> updateUser(@Valid @RequestBody UpdateUserDto updateUserDto,
            @AuthenticatedUser UserAuthInfo userInfo) {
        UserUpdatedMapped updatedUser = userService.updateUser(updateUserDto);
        return new CustomResponse<UserUpdatedMapped>(
                "Usuario actualizado exitosamente",
                updatedUser,
                HttpStatus.OK.value());
    }

    @DeleteMapping("/{idUser}")
    public CustomResponse<Boolean> deleteUser(@PathVariable Long idUser,
            @AuthenticatedUser UserAuthInfo userInfo) {
        userService.deleteUser(idUser);
        return new CustomResponse<Boolean>(
                "Usuario eliminado exitosamente",
                true,
                HttpStatus.OK.value());
    }

    @GetMapping
    @Public()
    public CustomResponse<List<UserMapped>> findAll() {
        return new CustomResponse<List<UserMapped>>(
                "Usuarios obtenidos correctamente",
                userService.findAll(),
                HttpStatus.OK.value());
    }
}
