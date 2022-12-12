package fun.is.quarkus.book_catalog.collaborators.stargate.api;

import fun.is.quarkus.book_catalog.collaborators.stargate.model.Credentials;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;


/**
  * Stargate Document API reference
  * <p/>The Stargate Document API provides CRUD operations on document data managed by Stargate.
  */
@Path("/v1/auth")
@RegisterRestClient(configKey="stargate_auth")
@ApplicationScoped
public interface AuthApi {

    /**
     * Create an auth token
     *
     * Create an authorization token.
     *
     */
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Uni<Response> createToken(
        Credentials credentials
    );

}
