package me.kendler.yanik.repositories.scene;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import me.kendler.yanik.dto.scene.SceneSelectAttributeCreateDTO;
import me.kendler.yanik.dto.scene.SceneSelectAttributeOptionSearchDTO;
import me.kendler.yanik.model.scene.attributeDefinitions.SceneAttributeDefinitionBase;
import me.kendler.yanik.model.scene.attributeDefinitions.SceneSelectAttributeOptionDefinition;

import java.util.List;

@ApplicationScoped
@Transactional
public class SceneSelectAttributeOptionDefinitionRepository implements PanacheRepository<SceneSelectAttributeOptionDefinition> {
    @Inject
    SceneAttributeDefinitionRepository sceneAttributeDefinitionRepository;

    public SceneSelectAttributeOptionDefinition create(SceneSelectAttributeCreateDTO createDTO){
        SceneAttributeDefinitionBase sceneAttributeDefinition = sceneAttributeDefinitionRepository.findById(createDTO.selectAttributeId());
        SceneSelectAttributeOptionDefinition sceneSelectAttributeOptionDefinition = new SceneSelectAttributeOptionDefinition(createDTO.name(), sceneAttributeDefinition);
        persist(sceneSelectAttributeOptionDefinition);
        return sceneSelectAttributeOptionDefinition;
    }

    public List<SceneSelectAttributeOptionDefinition> search(SceneSelectAttributeOptionSearchDTO searchDTO){
        return find("lower(name) like lower(concat('%', ?2, '%')) " +
                    "and sceneAttributeDefinition.id = ?1 " +
                    "order by name",
                    searchDTO.sceneAttributeDefinitionId(),
                    searchDTO.searchTerm()
        ).list();
    }

    public SceneSelectAttributeOptionDefinition delete(Long id){
        SceneSelectAttributeOptionDefinition sceneSelectAttributeOptionDefinition = findById(id);
        if(sceneSelectAttributeOptionDefinition != null) {
            delete(sceneSelectAttributeOptionDefinition);
        }
        return sceneSelectAttributeOptionDefinition;
    }
}
