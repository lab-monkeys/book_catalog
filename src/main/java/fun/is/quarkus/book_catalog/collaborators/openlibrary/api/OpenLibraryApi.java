package fun.is.quarkus.book_catalog.collaborators.openlibrary.api;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import io.smallrye.mutiny.Uni;

@Path("/api")
@RegisterRestClient(configKey = "open_library_api")
@ApplicationScoped
public interface OpenLibraryApi {
    
    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getBookInfo(@QueryParam("bibkeys") final String isbn, @QueryParam("format") final String format, @QueryParam("jscmd") final String jscmd);
}
