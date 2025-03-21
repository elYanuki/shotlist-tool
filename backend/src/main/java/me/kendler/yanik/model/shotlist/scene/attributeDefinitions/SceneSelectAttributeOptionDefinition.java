package me.kendler.yanik.model.shotlist.scene.attributeDefinitions;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "sceneselectattributeoptiondefinition")
public class SceneSelectAttributeOptionDefinition extends PanacheEntity {
    public String name;
    public int position;
}