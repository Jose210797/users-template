package com.template.users.modules.users.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.template.users.config.constants.Schemas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users", schema = Schemas.AUTH)
public class UserEntity {
    
    @Id()
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @Column(nullable = false, unique = true, length = 100)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(name = "created_at")
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private ZonedDateTime updatedAt;


    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active = true;


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


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
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


    public Boolean getActive() {
        return active;
    }


    public void setActive(Boolean active) {
        this.active = active;
    }

   

}
