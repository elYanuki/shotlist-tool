package me.kendler.yanik.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import me.kendler.yanik.dto.CreateShotlistDTO;
import me.kendler.yanik.repositories.scene.SceneRepository;

import java.util.UUID;

@Path("/shotlist/{shotlistId}/scene")
public class SceneResource {
    @Inject
    SceneRepository sceneRepository;

    @GET
    public Response getAll(@PathParam("shotlistId") String shotlistId) {
        return Response.ok().entity(sceneRepository.list("shotlist.id", shotlistId)).build();
    }

    @POST
    public Response create(@PathParam("shotlistId") String shotlistId) {
        return Response.ok().entity(sceneRepository.create(shotlistId)).build();
    }

    @DELETE
    public Response delete(@QueryParam("id") UUID id) {
        sceneRepository.deleteById(id);
        return Response.ok().build();
    }
}
