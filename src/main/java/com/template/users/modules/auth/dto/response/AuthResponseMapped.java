package com.template.users.modules.auth.dto.response;

import java.util.List;

public class AuthResponseMapped {
    private String accessToken;
    private String refreshToken;
    private String username;
    private List<Long> modules;//modulos que tiene el usuario
    private List<Long> actions;//acciones que tiene el usuario

    public AuthResponseMapped() {

    }

    public AuthResponseMapped(String accessToken, String refreshToken, String username, List<Long> modules,
            List<Long> actions) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.modules = modules;
        this.actions = actions;
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Long> getModules() {
        return modules;
    }

    public void setModules(List<Long> modules) {
        this.modules = modules;
    }

    public List<Long> getActions() {
        return actions;
    }

    public void setActions(List<Long> actions) {
        this.actions = actions;
    }

}