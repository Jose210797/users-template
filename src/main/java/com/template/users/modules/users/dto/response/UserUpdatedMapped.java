package com.template.users.modules.users.dto.response;

import java.time.ZonedDateTime;

public class UserUpdatedMapped {
    private Long idUser;
    private String username;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public UserUpdatedMapped(Long idUser, String username, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.idUser = idUser;
        this.username = username;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    

}
