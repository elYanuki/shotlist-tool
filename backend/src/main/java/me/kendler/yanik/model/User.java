package me.kendler.yanik.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import me.kendler.yanik.model.template.Template;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "app_user")
public class User extends PanacheEntityBase {
    @Id
    @GeneratedValue
    public UUID id;
    public String username;
    public String email;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    public Set<Shotlist> shotlists;
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    public Set<Template> templates;
    public LocalDateTime createdAt;

    public User() {
        this.createdAt = LocalDateTime.now();
    }

    public User(String username, String email) {
        this();
        this.username = username;
        this.email = email;
    }
}