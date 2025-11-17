package com.template.users.modules.roles.entity;

import java.time.ZonedDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.template.users.config.constants.Schemas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "role_actions", schema = Schemas.AUTH)
public class RoleAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role_action")
    private Long idRoleAction;

    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "id_action", nullable = false)
    private ActionEntity action;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private ZonedDateTime createAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private ZonedDateTime updateAt;

    @Column(name = "active", nullable = false, columnDefinition = "boolean default true")
    private Boolean active;

    public Long getIdRoleAction() {
        return idRoleAction;
    }

    public void setIdRoleAction(Long idRoleAction) {
        this.idRoleAction = idRoleAction;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public ActionEntity getAction() {
        return action;
    }

    public void setAction(ActionEntity action) {
        this.action = action;
    }

    public ZonedDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(ZonedDateTime createAt) {
        this.createAt = createAt;
    }

    public ZonedDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(ZonedDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    
}
