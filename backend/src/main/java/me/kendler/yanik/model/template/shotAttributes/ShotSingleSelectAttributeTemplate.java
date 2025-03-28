package me.kendler.yanik.model.template.shotAttributes;

import java.util.*;

import jakarta.persistence.*;
import me.kendler.yanik.model.Shotlist;
import me.kendler.yanik.model.shot.attributeDefinitions.ShotAttributeDefinitionBase;
import me.kendler.yanik.model.shot.attributeDefinitions.ShotMultiSelectAttributeDefinition;
import me.kendler.yanik.model.shot.attributeDefinitions.ShotSelectAttributeOptionDefinition;
import me.kendler.yanik.model.shot.attributeDefinitions.ShotSingleSelectAttributeDefinition;

@Entity
public class ShotSingleSelectAttributeTemplate extends ShotAttributeTemplateBase {
    @OneToMany
    public Set<ShotSelectAttributeOptionTemplate> options;

    @Override
    public String getType() {
        return "ShotSingleSelect";
    }

    @Override
    public ShotAttributeDefinitionBase createDefinition(Shotlist shotlist) {
        Set<ShotSelectAttributeOptionDefinition> optionDefinitions = new HashSet<>();

        for (ShotSelectAttributeOptionTemplate option : options) {
            ShotSelectAttributeOptionDefinition optionDefinition = option.createDefinition();
            persist(optionDefinition);
            optionDefinitions.add(optionDefinition);
        }

        return new ShotSingleSelectAttributeDefinition(shotlist, this.name, optionDefinitions);
    }
}