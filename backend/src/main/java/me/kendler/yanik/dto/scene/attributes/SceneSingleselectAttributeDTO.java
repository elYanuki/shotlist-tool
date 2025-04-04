package me.kendler.yanik.dto.scene.attributes;

import me.kendler.yanik.model.scene.Scene;
import me.kendler.yanik.model.scene.attributeDefinitions.SceneAttributeDefinitionBase;
import me.kendler.yanik.model.scene.attributeDefinitions.SceneSelectAttributeOptionDefinition;

import java.util.List;

public record SceneSingleselectAttributeDTO(
        Long id,
        SceneAttributeDefinitionBase definition,
        SceneSelectAttributeOptionDefinition singleselectValue
) implements SceneAttributeBaseDTO{
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public SceneAttributeDefinitionBase getDefinition() {
        return definition;
    }
}
