package fun.is.quarkus.book_catalog.collaborators.openlibrary.api;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import fun.is.quarkus.book_catalog.collaborators.openlibrary.dto.BookDto;

@Path("/api")
@RegisterRestClient(configKey = "open_library_api")
@ApplicationScoped
public interface OpenLibraryApi {
    
    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public BookDto getBookInfo(@QueryParam("bibkeys") final String isbn, @QueryParam("format") final String format, @QueryParam("jscmd") final String jscmd);
}
