package me.kendler.yanik.repositories.shot;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import me.kendler.yanik.model.scene.Scene;
import me.kendler.yanik.model.shot.Shot;
import me.kendler.yanik.repositories.scene.SceneRepository;

import java.util.UUID;

@ApplicationScoped
@Transactional
public class ShotRepository implements PanacheRepositoryBase<Shot, UUID> {
    @Inject
    SceneRepository sceneRepository;

    public Shot create(UUID sceneId) {
        Scene scene = sceneRepository.findById(sceneId);
        Shot shot = new Shot(scene);
        persist(shot);
        return shot;
    }

    public Shot delete(UUID id) {
        Shot shot = findById(id);
        if (shot != null) {
            delete(shot);
            return shot;
        }
        return null;
    }
}