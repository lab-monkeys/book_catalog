package fun.is.quarkus.book_catalog.api;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import fun.is.quarkus.book_catalog.dto.BookInfoDto;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
@Path("/book-info")
public interface BookInfoApi {
    
    @GET
    @Path("/book-by-id/{catalog-id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getBookById(@PathParam("catalog-id") String catalogId);

    @GET
    @Path("/book-by-isbn/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getBookByIsbn(@PathParam("isbn") String isbn);

    @GET
    @Path("/books-by-author/{author}/{num_results}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getBooksByAuthor(@PathParam("author") String author, @PathParam("num_results") Integer numResults);

    @GET
    @Path("/open-library/{isbn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getOpenLibraryBookByIsbn(@PathParam("isbn") String isbn);

    @POST
    @Path("/save-book")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> saveBookInfo(BookInfoDto dto);
}