package com.template.users.modules.auth.mappers;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.template.users.modules.auth.dto.response.AuthResponseMapped;
import com.template.users.modules.roles.entity.ActionEntity;
import com.template.users.modules.roles.entity.ModuleEntity;

@Component
public class AuthMapper {
    public AuthMapper() {
    }

    public AuthResponseMapped toAuthResponseMapped(String accessToken, String refreshToken, String username, List<Long> modules,
            List<Long> actions) {
        AuthResponseMapped authResponseMapped = new AuthResponseMapped();
        authResponseMapped.setAccessToken(accessToken);
        authResponseMapped.setRefreshToken(refreshToken);
        authResponseMapped.setUsername(username);
        authResponseMapped.setModules(modules);
        authResponseMapped.setActions(actions);
        return authResponseMapped;
    }

    public List<Long> toModuleidList(List<ActionEntity> acciones) {
         return acciones.stream()
            .map(ActionEntity::getModule)
            .filter(Objects::nonNull)
            .map(ModuleEntity::getIdModule)
            .distinct()
            .toList();
    }
}
