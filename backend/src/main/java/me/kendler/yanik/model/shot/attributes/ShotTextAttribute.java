package me.kendler.yanik.model.shot.attributes;

import jakarta.persistence.*;
import me.kendler.yanik.dto.shot.attributes.ShotAttributeBaseDTO;
import me.kendler.yanik.dto.shot.attributes.ShotTextAttributeDTO;
import me.kendler.yanik.model.shot.Shot;
import me.kendler.yanik.model.shot.attributeDefinitions.ShotTextAttributeDefinition;

@Entity
public class ShotTextAttribute extends ShotAttributeBase {
    @ManyToOne
    public ShotTextAttributeDefinition definition;
    public String value;

    public ShotTextAttribute() { }

    public ShotTextAttribute(ShotTextAttributeDefinition definition, Shot shot) {
        super(shot);
        this.definition = definition;
    }

    @Override
    public ShotAttributeBaseDTO toDTO() {
        return new ShotTextAttributeDTO(
            id,
            shot,
            definition,
            value
        );
    }
}