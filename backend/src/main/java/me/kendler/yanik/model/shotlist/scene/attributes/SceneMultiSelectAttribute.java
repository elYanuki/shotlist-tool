package me.kendler.yanik.model.shotlist.scene.attributes;

import java.util.*;

import jakarta.persistence.*;
import me.kendler.yanik.model.shotlist.scene.attributeDefinitions.SceneMultiSelectAttributeDefinition;
import me.kendler.yanik.model.shotlist.scene.attributeDefinitions.SceneSelectAttributeOptionDefinition;

@Entity
public class SceneMultiSelectAttribute extends SceneAttributeBase {
    @ManyToOne
    public SceneMultiSelectAttributeDefinition definition;
    @OneToMany
    public List<SceneSelectAttributeOptionDefinition> value;
}