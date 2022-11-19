package fun.is.quarkus.book_catalog.collaborators.stargate.api;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.enterprise.context.ApplicationScoped;

/**
  * Stargate Document API reference
  * <p/>The Stargate Document API provides CRUD operations on document data managed by Stargate.
  */
@Path("/v2/schemas/namespaces")
@RegisterRestClient(baseUri="https://localhost:8082", configKey="stargate_json")
@ApplicationScoped
public interface SchemasApi {

    /**
     * Create a namespace
     *
     */
    @POST
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Uni<Response> createNamespace(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        Object body
    );

    /**
     * Delete a namespace
     *
     */
    @DELETE
    @Path("/{namespace-id}")
    @Produces({"application/json"})
    public Uni<Response> deleteNamespace(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId
    );

    /**
     * Get all namespaces
     *
     * Retrieve all available namespaces.
     *
     */
    @GET
    @Produces({"application/json"})
    public Uni<Response> getAllNamespaces(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @QueryParam("raw") Boolean raw
    );

    /**
     * Get a namespace
     *
     */
    @GET
    @Path("/{namespace-id}")
    @Produces({"application/json"})
    public Uni<Response> getNamespace(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @QueryParam("raw") Boolean raw
    );

}
