package me.kendler.yanik.model.shot.attributeDefinitions;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
public class ShotSelectAttributeOptionDefinition extends PanacheEntity {
    public String name = "";
    @ManyToOne()
    public ShotAttributeDefinitionBase shotAttributeDefinition;

    public ShotSelectAttributeOptionDefinition() { }

    public ShotSelectAttributeOptionDefinition(String name, ShotAttributeDefinitionBase shotAttributeDefinition) {
        this.name = name;
        this.shotAttributeDefinition = shotAttributeDefinition;
    }

    public ShotSelectAttributeOptionDefinition(ShotAttributeDefinitionBase shotAttributeDefinition) {
        this.shotAttributeDefinition = shotAttributeDefinition;
    }
}