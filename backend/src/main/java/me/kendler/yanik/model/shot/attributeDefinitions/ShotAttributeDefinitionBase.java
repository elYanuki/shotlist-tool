package me.kendler.yanik.model.shot.attributeDefinitions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import me.kendler.yanik.model.Shotlist;
import me.kendler.yanik.model.scene.SceneAttributeType;
import me.kendler.yanik.model.shot.Shot;
import me.kendler.yanik.model.shot.ShotAttributeType;
import me.kendler.yanik.model.shot.attributes.ShotAttributeBase;

/**
 * Base class for shot attribute definitions.
 * Defines all the attributes that every single shot in a shotlist has, stores the selection options and names for those attributes.
 */
@Entity
@Table(name = "shotattributedefinition")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ShotAttributeDefinitionBase extends PanacheEntity {
    /*@ManyToOne
    @JsonIgnore
    public Shotlist shotlist;*/
    public String name;
    public int position;

    public ShotAttributeDefinitionBase() { }

    public ShotAttributeDefinitionBase(Shotlist shotlist) {
        //this.shotlist = shotlist;
        this.position = shotlist.shotAttributeDefinitions.size();
        shotlist.shotAttributeDefinitions.add(this);
        System.out.println(shotlist);
    }

    public ShotAttributeDefinitionBase(Shotlist shotlist, String name) {
        this(shotlist);
        this.name = name;
    }

    abstract public ShotAttributeType getType();

    abstract public ShotAttributeBase createAttribute(Shot shot);

    @Override
    public String toString() {
        return "ShotAttributeDefinition{" +
                ", name='" + name + '\'' +
                ", position=" + position +
                '}';
    }
}
