package me.kendler.yanik.model.template;

import java.util.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import me.kendler.yanik.model.template.sceneAttributes.SceneAttributeTemplateBase;
import me.kendler.yanik.model.template.shotAttributes.ShotAttributeTemplateBase;
import me.kendler.yanik.model.User;

@Entity
public class Template extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;
    @ManyToOne
    @JsonIgnore
    public User owner;
    @OneToMany(mappedBy = "template")
    public Set<SceneAttributeTemplateBase> sceneAttributes;
    @OneToMany(mappedBy = "template")
    public Set<ShotAttributeTemplateBase> shotAttributes;
    public String name;
    public LocalDateTime createdAt;

    public Template() {
        this.createdAt = LocalDateTime.now();
    }
}