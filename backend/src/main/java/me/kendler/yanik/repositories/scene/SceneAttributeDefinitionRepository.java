package me.kendler.yanik.repositories.scene;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import me.kendler.yanik.dto.scene.SceneAttributeDefinitionCreateDTO;
import me.kendler.yanik.dto.scene.SceneAttributeDefinitionEditDTO;
import me.kendler.yanik.dto.scene.attributeDefinitions.SceneAttributeDefinitionBaseDTO;
import me.kendler.yanik.dto.scene.attributeDefinitions.SceneMultiSelectAttributeDefinitionDTO;
import me.kendler.yanik.dto.scene.attributeDefinitions.SceneSingleSelectAttributeDefinitionDTO;
import me.kendler.yanik.model.Shotlist;
import me.kendler.yanik.model.scene.Scene;
import me.kendler.yanik.model.scene.attributeDefinitions.*;
import me.kendler.yanik.model.scene.attributes.SceneAttributeBase;
import me.kendler.yanik.repositories.ShotlistRepository;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@ApplicationScoped
public class SceneAttributeDefinitionRepository implements PanacheRepository<SceneAttributeDefinitionBase> {
    @Inject
    ShotlistRepository shotlistRepository;

    @Inject
    SceneAttributeRepository sceneAttributeRepository;

    public List<SceneAttributeDefinitionBaseDTO> getAll(UUID shotlistId) {
        Shotlist shotlist = shotlistRepository.findById(shotlistId);

        if (shotlist == null) {
            throw new IllegalArgumentException("Shotlist not found");
        }

        Set<SceneAttributeDefinitionBase> attributeDefinitions = shotlist.sceneAttributeDefinitions;

        List<SceneSelectAttributeOptionDefinition> options = SceneSelectAttributeOptionDefinition.find("sceneAttributeDefinition in ?1", attributeDefinitions).list();

        List<SceneAttributeDefinitionBaseDTO> attributeDefinitionDTOs = new ArrayList<>();

        attributeDefinitions.forEach(definition -> {
            switch (definition) {
                case SceneSingleSelectAttributeDefinition singleSelectAttribute -> {
                    attributeDefinitionDTOs.add(new SceneSingleSelectAttributeDefinitionDTO(
                            definition.id,
                            definition.name,
                            definition.position,
                            options.stream().filter(option -> option.sceneAttributeDefinition.id.equals(definition.id)).toList()
                    ));
                }
                case SceneMultiSelectAttributeDefinition multiSelectAttribute -> {
                    attributeDefinitionDTOs.add(new SceneMultiSelectAttributeDefinitionDTO(
                            definition.id,
                            definition.name,
                            definition.position,
                            options.stream().filter(option -> option.sceneAttributeDefinition.id.equals(definition.id)).toList()
                    ));
                }
                default -> {
                    attributeDefinitionDTOs.add(definition.toDTO());
                }
            }
        });

        return attributeDefinitionDTOs.stream().sorted(Comparator.comparingInt(SceneAttributeDefinitionBaseDTO::getPosition)).collect(Collectors.toList());
    }

    public SceneAttributeDefinitionBase create(SceneAttributeDefinitionCreateDTO createDTO){
        if(createDTO == null) {
            throw new IllegalArgumentException("SceneAttributeDefinitionCreateDTO cannot be null");
        }
        if(createDTO.shotlistId() == null) {
            throw new IllegalArgumentException("Shotlist ID cannot be null");
        }
        if(createDTO.type() == null) {
            throw new IllegalArgumentException("SceneAttributeDefinition type cannot be null");
        }

        SceneAttributeDefinitionBase attributeDefinition = null;
        Shotlist shotlist = shotlistRepository.findById(createDTO.shotlistId());

        shotlist.registerEdit();

        switch (createDTO.type()) {
            case SceneSingleSelectAttribute -> {
                attributeDefinition = new SceneSingleSelectAttributeDefinition(shotlist);
            }
            case SceneMultiSelectAttribute -> {
                attributeDefinition = new SceneMultiSelectAttributeDefinition(shotlist);
            }
            case SceneTextAttribute -> {
                attributeDefinition = new SceneTextAttributeDefinition(shotlist);
            }
        }

        if(attributeDefinition == null) {
            throw new IllegalArgumentException("Invalid attribute definition type");
        }

        persist(attributeDefinition);

        SceneAttributeDefinitionBase finalAttributeDefinition = attributeDefinition;
        shotlist.scenes.forEach(scene -> {
            sceneAttributeRepository.persist(finalAttributeDefinition.createAttribute(scene));
        });

        return attributeDefinition;
    }

    public SceneAttributeDefinitionBase update(SceneAttributeDefinitionEditDTO editDTO) {
        SceneAttributeDefinitionBase attribute = findById(editDTO.id());
        if (attribute == null) {
            throw new IllegalArgumentException("Attribute not found");
        }

        Shotlist shotlist = getShotlistByDefinitionId(editDTO.id());

        shotlist.registerEdit();

        if(editDTO.name() != null && !editDTO.name().isEmpty()) {
            attribute.name = editDTO.name();
        }
        if(editDTO.position() != null && attribute.position != editDTO.position()){
            shotlist.sceneAttributeDefinitions.stream()
                    .filter(a -> a.position < attribute.position && a.position >= editDTO.position())
                    .forEach(a -> a.position++);
            shotlist.sceneAttributeDefinitions.stream()
                    .filter(a -> a.position > attribute.position && a.position <= editDTO.position())
                    .forEach(a -> a.position--);

            attribute.position = editDTO.position();
        }

        getEntityManager().merge(attribute);

        return attribute;
    }

    public SceneAttributeDefinitionBase delete(Long id){
        SceneAttributeDefinitionBase attributeDefinition = findById(id);

        if(attributeDefinition != null) {
            List<SceneAttributeBase> relevantAttributes = getEntityManager()
                    .createQuery("select sa from SceneAttributeBase sa where sa.definition.id = :definitionId", SceneAttributeBase.class)
                    .setParameter("definitionId", id)
                    .getResultList();

            List<Scene> relevantScenes = getEntityManager()
                    .createQuery("select s from Scene s join s.attributes sab where sab.definition.id = :definitionId", Scene.class)
                    .setParameter("definitionId", id)
                    .getResultList();

            relevantScenes.forEach(scene -> {
                relevantAttributes.forEach(scene.attributes::remove);
            });

            Shotlist relevantShotlist = getShotlistByDefinitionId(id);

            relevantShotlist.sceneAttributeDefinitions.remove(attributeDefinition);

            delete(attributeDefinition);

            relevantShotlist.registerEdit();

            return attributeDefinition;
        }
        return null;
    }

    public Shotlist getShotlistByDefinitionId(Long id) {
        return getEntityManager().createQuery("select s from Shotlist s join s.sceneAttributeDefinitions d where d.id = :definitionId", Shotlist.class)
                .setParameter("definitionId", id)
                .getSingleResult();
    }
}
