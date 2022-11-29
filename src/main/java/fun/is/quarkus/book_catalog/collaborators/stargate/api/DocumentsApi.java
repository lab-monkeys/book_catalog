package fun.is.quarkus.book_catalog.collaborators.stargate.api;


import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;

import io.smallrye.mutiny.Uni;

/**
  * Stargate Document API reference
  * <p/>The Stargate Document API provides CRUD operations on document data managed by Stargate.
  */
@Path("/v2")
@RegisterRestClient(baseUri="https://localhost:8082", configKey="stargate_json")
@ApplicationScoped
public interface DocumentsApi {

    /**
     * Add a new document to {collection-id}
     *
     */
    @POST
    @Path("/namespaces/{namespace-id}/collections/{collection-id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Uni<Response> addDoc(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @PathParam("collection-id") String collectionId, 
        Object body
    );

    /**
     * Write multiple new documents in one request
     *
     */
    @POST
    @Path("/namespaces/{namespace-id}/collections/{collection-id}/batch")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Uni<Response> addManyDocs(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @PathParam("collection-id") String collectionId, 
        Object body, 
        @QueryParam("id-path") String idPath
    );

    /**
     * Create a collection
     *
     */
    @POST
    @Path("/namespaces/{namespace-id}/collections")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Uni<Response> createCollection(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        Object body
    );

    /**
     * Delete a collection
     *
     */
    @DELETE
    @Path("/schemas/namespaces/{namespace-id}/collections/{collection-id}")
    @Produces({"application/json"})
    public Uni<Response> deleteCollectionSchema(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @PathParam("collection-id") String collectionId
    );

    /**
     * Delete a  document
     *
     */
    @DELETE
    @Path("/namespaces/{namespace-id}/collections/{collection-id}/{document-id}")
    @Produces({"application/json"})
    public Uni<Response> deleteDoc(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @PathParam("collection-id") String collectionId, 
        @PathParam("document-id") String documentId
    );

    /**
     * Delete a sub document by {document-path}
     *
     */
    @DELETE
    @Path("/namespaces/{namespace-id}/collections/{collection-id}/{document-id}/{document-path}")
    @Produces({"application/json"})
    public Uni<Response> deleteSubDoc(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @PathParam("collection-id") String collectionId, 
        @PathParam("document-id") String documentId, 
        @PathParam("document-path") String documentPath
    );

    /**
     * Get a collection
     *
     */
    @GET
    @Path("/schemas/namespaces/{namespace-id}/collections/{collection-id}")
    @Produces({"application/json"})
    public Uni<Response> getCollection(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @PathParam("collection-id") String collectionId, 
        @QueryParam("raw") Boolean raw
    );

    /**
     * Get a document by {document-id}
     *
     */
    @GET
    @Path("/namespaces/{namespace-id}/collections/{collection-id}/{document-id}")
    @Produces({"application/json"})
    public Uni<Response> getDocById(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @PathParam("collection-id") String collectionId, 
        @PathParam("document-id") String documentId, 
        @QueryParam("fields") String fields, 
        @QueryParam("raw") Boolean raw
    );

    /**
     * Get a sub document by {document-path}
     *
     */
    @GET
    @Path("/namespaces/{namespace-id}/collections/{collection-id}/{document-id}/{document-path}")
    @Produces({"application/json"})
    public Uni<Response> getSubDocByPath(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @PathParam("collection-id") String collectionId, 
        @PathParam("document-id") String documentId, 
        @PathParam("document-path") String documentPath, 
        @QueryParam("fields") String fields, 
        @QueryParam("raw") Boolean raw
    );

    /**
     * List collections in a namespace
     *
     */
    @GET
    @Path("/namespaces/{namespace-id}/collections")
    @Produces({"application/json"})
    public Uni<Response> listCollections(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @QueryParam("raw") Boolean raw
    );

    /**
     * Replace a document by {document-id}
     *
     */
    @PUT
    @Path("/namespaces/{namespace-id}/collections/{collection-id}/{document-id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Uni<Response> replaceDoc(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @PathParam("collection-id") String collectionId, 
        @PathParam("document-id") String documentId, 
        Object body
    );

    /**
     * Replace a sub document by {document-path}
     *
     */
    @PUT
    @Path("/namespaces/{namespace-id}/collections/{collection-id}/{document-id}/{document-path}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Uni<Response> replaceSubDoc(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @PathParam("collection-id") String collectionId, 
        @PathParam("document-id") String documentId, 
        @PathParam("document-path") String documentPath, 
        Object body
    );

    /**
     * Search for documents in {collection-id}
     *
     */
    @GET
    @Path("/namespaces/{namespace-id}/collections/{collection-id}")
    @Produces({"application/json"})
    public Uni<Response> searchDoc(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @PathParam("collection-id") String collectionId, 
        @QueryParam("where") Object where, 
        @QueryParam("fields") String fields, 
        @QueryParam("page-size") Integer pageSize, 
        @QueryParam("page-state") String pageState, 
        @QueryParam("raw") Boolean raw
    );

    /**
     * Update part of a document
     *
     */
    @PATCH
    @Path("/namespaces/{namespace-id}/collections/{collection-id}/{document-id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Uni<Response> updatePartOfDoc(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @PathParam("collection-id") String collectionId, 
        @PathParam("document-id") String documentId, 
        Object body
    );

    /**
     * Update part of a sub document by {document-path}
     *
     */
    @PATCH
    @Path("/namespaces/{namespace-id}/collections/{collection-id}/{document-id}/{document-path}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Uni<Response> updatePartOfSubDoc(
        @HeaderParam("X-Cassandra-Token") String xCassandraToken, 
        @PathParam("namespace-id") String namespaceId, 
        @PathParam("collection-id") String collectionId, 
        @PathParam("document-id") String documentId, 
        @PathParam("document-path") String documentPath, 
        Object body
    );

}
