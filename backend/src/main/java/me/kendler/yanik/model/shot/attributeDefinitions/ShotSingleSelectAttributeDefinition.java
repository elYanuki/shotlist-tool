package me.kendler.yanik.model.shot.attributeDefinitions;

import java.util.*;

import jakarta.persistence.*;
import me.kendler.yanik.model.Shotlist;
import me.kendler.yanik.model.shot.Shot;
import me.kendler.yanik.model.shot.ShotAttributeType;
import me.kendler.yanik.model.shot.attributes.ShotAttributeBase;
import me.kendler.yanik.model.shot.attributes.ShotSingleSelectAttribute;

@Entity
@DiscriminatorValue("ShotSingleSelect")
public class ShotSingleSelectAttributeDefinition extends ShotAttributeDefinitionBase {
    @OneToMany(fetch = FetchType.EAGER)
    public Set<ShotSelectAttributeOptionDefinition> options = new HashSet<>();

    public ShotSingleSelectAttributeDefinition() { super(); }

    public ShotSingleSelectAttributeDefinition(Shotlist shotlist) {
        super(shotlist);
    }

    public ShotSingleSelectAttributeDefinition(Shotlist shotlist, String name, Set<ShotSelectAttributeOptionDefinition> options) {
        super(shotlist, name);
        this.options = options;
    }

    @Override
    public ShotAttributeType getType() {
        return ShotAttributeType.ShotSingleSelectAttribute;
    }

    @Override
    public ShotAttributeBase createAttribute(Shot shot) {
        return new ShotSingleSelectAttribute(this, shot);
    }

    @Override
    public boolean addOption(ShotSelectAttributeOptionDefinition option) {
        options.add(option);
        return true;
    }
}