package com.template.users.modules.auth.services;

import com.template.users.modules.auth.dto.request.LoginDto;
import com.template.users.modules.auth.dto.response.AuthResponseMapped;
import com.template.users.modules.auth.mappers.AuthMapper;
import com.template.users.modules.roles.entity.ActionEntity;
import com.template.users.modules.roles.repository.IActionRepository;
import com.template.users.modules.users.entity.UserEntity;
import com.template.users.shared.security.services.CustomUserDetailsService;
import com.template.users.shared.security.utils.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService {

    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final IActionRepository actionRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthMapper authMapper;

    public AuthService(CustomUserDetailsService userDetailsService,
            JwtUtil jwtUtil,
            IActionRepository actionRepository,
            PasswordEncoder passwordEncoder,
            AuthMapper authMapper) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.actionRepository = actionRepository;
        this.passwordEncoder = passwordEncoder;
        this.authMapper = authMapper;
    }

    public AuthResponseMapped login(LoginDto loginDto) {
        try {

            // Obtener el usuario de la base de datos
            UserEntity user;
            try {
                user = userDetailsService.getUserByUsername(loginDto.getUsername());
            } catch (UsernameNotFoundException e) {
                throw new BadCredentialsException("Usuario o contraseña incorrectos");
            }

            // Validar la contraseña
            if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Usuario o contraseña incorrectos");
            }

            String accessToken = jwtUtil.generateToken(user.getUsername(), user.getIdUser());
            String refreshToken = jwtUtil.generateRefreshToken(user.getUsername(), user.getIdUser());
            System.out.println("Llego al punto 1");
            List<Long> userActionIds = userDetailsService.getUserActionIds(user.getIdUser());
            System.out.println("Llego al punto 2");
            List<ActionEntity> userActions = actionRepository.findAllById(userActionIds);
            System.out.println("Llego al punto 3");
            List<Long> modulesIds = authMapper.toModuleidList(userActions);

            return authMapper.toAuthResponseMapped(
                    accessToken,
                    refreshToken,
                    user.getUsername(),
                    modulesIds,
                    userActionIds);

        } catch (Exception e) {
            throw new BadCredentialsException("Credenciales inválidas");
        }
    }

    public AuthResponseMapped refreshToken(String refreshToken) {
        try {
            if (!jwtUtil.isValidToken(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
                throw new IllegalArgumentException("Token de actualización inválido");
            }

            String username = jwtUtil.extractUsername(refreshToken);
            Long userId = jwtUtil.extractUserId(refreshToken);

            UserEntity user = userDetailsService.getUserByUsername(username);

            String newAccessToken = jwtUtil.generateToken(username, userId);
            String newRefreshToken = jwtUtil.generateRefreshToken(username, userId);
            List<Long> userActionIds = userDetailsService.getUserActionIds(user.getIdUser());
            List<ActionEntity> userActions = actionRepository.findAllById(userActionIds);
            List<Long> modulesIds = authMapper.toModuleidList(userActions);

            return authMapper.toAuthResponseMapped(
                    newAccessToken,
                    newRefreshToken,
                    user.getUsername(),
                    modulesIds,
                    userActionIds);

        } catch (Exception e) {
            throw new IllegalArgumentException("Error al refrescar el token: " + e.getMessage());
        }
    }
}