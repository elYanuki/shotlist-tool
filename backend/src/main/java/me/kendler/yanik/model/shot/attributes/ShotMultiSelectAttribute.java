package me.kendler.yanik.model.shot.attributes;

import java.util.*;

import jakarta.persistence.*;
import me.kendler.yanik.dto.shot.attributes.ShotAttributeBaseDTO;
import me.kendler.yanik.dto.shot.ShotAttributeEditDTO;
import me.kendler.yanik.dto.shot.attributes.ShotMultiSelectAttributeDTO;
import me.kendler.yanik.model.shot.Shot;
import me.kendler.yanik.model.shot.attributeDefinitions.ShotMultiSelectAttributeDefinition;
import me.kendler.yanik.model.shot.attributeDefinitions.ShotSelectAttributeOptionDefinition;

@Entity
public class ShotMultiSelectAttribute extends ShotAttributeBase {
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Set<ShotSelectAttributeOptionDefinition> value = new HashSet<>();

    public ShotMultiSelectAttribute() { }

    public ShotMultiSelectAttribute(ShotMultiSelectAttributeDefinition definition, Shot shot) {
        super(shot, definition);
    }

    @Override
    public ShotAttributeBaseDTO toDTO() {
        return new ShotMultiSelectAttributeDTO(
            id,
            definition.toDTO(),
            value.stream().sorted(Comparator.comparing(option -> option.name)).toList()
        );
    }
}