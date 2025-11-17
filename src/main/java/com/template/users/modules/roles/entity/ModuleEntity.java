package com.template.users.modules.roles.entity;

import java.util.List;

import com.template.users.config.constants.Schemas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "modules",schema = Schemas.AUTH)
public class ModuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_module")
    private Long idModule;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @OneToMany(mappedBy = "module", orphanRemoval = true)
    private List<ActionEntity> actions;

    public Long getIdModule() {
        return idModule;
    }
    public void setIdModule(Long idModule) {
        this.idModule = idModule;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<ActionEntity> getActions() {
        return actions;
    }
    public void setActions(List<ActionEntity> actions) {
        this.actions = actions;
    }

    
    
}
