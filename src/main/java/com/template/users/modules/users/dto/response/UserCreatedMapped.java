package com.template.users.modules.users.dto.response;

public class UserCreatedMapped {
    private Long idUser;

    public UserCreatedMapped(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}