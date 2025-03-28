package me.kendler.yanik.model.shot.attributeDefinitions;

import jakarta.persistence.*;
import me.kendler.yanik.model.Shotlist;
import me.kendler.yanik.model.shot.Shot;
import me.kendler.yanik.model.shot.attributes.ShotAttributeBase;
import me.kendler.yanik.model.shot.attributes.ShotTextAttribute;

@Entity
public class ShotTextAttributeDefinition extends ShotAttributeDefinitionBase {
    public ShotTextAttributeDefinition() { super(); }

    public ShotTextAttributeDefinition(Shotlist shotlist, String name) {
        super(shotlist, name);
    }

    @Override
    public String getType() {
        return "ShotText";
    }

    @Override
    public ShotAttributeBase createAttribute(Shot shot) {
        return new ShotTextAttribute(this, shot);
    }
}