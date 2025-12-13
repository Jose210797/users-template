package com.template.users.shared.security.dto;

import java.util.List;

public class UserAuthInfo {
    private Long userId;
    private String username;
    private List<Long> actions;

    public UserAuthInfo() {}

    public UserAuthInfo(Long userId, String username, List<Long> actions) {
        this.userId = userId;
        this.username = username;
        this.actions = actions;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Long> getActions() {
        return actions;
    }

    public void setActions(List<Long> actions) {
        this.actions = actions;
    }

    public boolean hasAction(Long actionId) {
        return actions != null && actions.contains(actionId);
    }

    public boolean hasAnyAction(List<Long> actionIds) {
        if (actions == null || actionIds == null) return false;
        return actionIds.stream().anyMatch(actions::contains);
    }

    public boolean hasAllActions(List<Long> actionIds) {
        if (actions == null || actionIds == null) return false;
        return actions.containsAll(actionIds);
    }
}