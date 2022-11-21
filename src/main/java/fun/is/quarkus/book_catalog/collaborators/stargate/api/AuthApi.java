package fun.is.quarkus.book_catalog.collaborators.stargate.api;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import fun.is.quarkus.book_catalog.collaborators.stargate.model.Credentials;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

/**
  * Stargate Document API reference
  * <p/>The Stargate Document API provides CRUD operations on document data managed by Stargate.
  */

@RegisterRestClient(configKey="stargate_auth")
@ApplicationScoped
@Path("/v1")
public interface AuthApi {
    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createToken(Credentials credentials);
}
