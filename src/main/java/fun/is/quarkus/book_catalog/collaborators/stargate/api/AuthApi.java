package fun.is.quarkus.book_catalog.collaborators.stargate.api;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import fun.is.quarkus.book_catalog.collaborators.stargate.model.Credentials;
import javax.enterprise.context.ApplicationScoped;

/**
  * Stargate Document API reference
  * <p/>The Stargate Document API provides CRUD operations on document data managed by Stargate.
  */
@Path("/v1/auth")
@RegisterRestClient(baseUri="https://localhost:8082", configKey="stargate_json")
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
    public Response createToken(
        Credentials credentials
    );

}
