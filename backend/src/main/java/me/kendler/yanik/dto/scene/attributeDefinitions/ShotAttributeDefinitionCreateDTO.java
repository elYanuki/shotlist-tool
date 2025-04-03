package me.kendler.yanik.dto.scene.attributeDefinitions;

import me.kendler.yanik.model.shot.ShotAttributeType;

import java.util.UUID;

public record ShotAttributeDefinitionCreateDTO (
    UUID shotlistId,
    ShotAttributeType type
){ }
