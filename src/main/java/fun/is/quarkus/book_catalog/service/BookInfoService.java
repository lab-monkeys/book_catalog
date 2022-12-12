package fun.is.quarkus.book_catalog.service;

import java.time.Duration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import fun.is.quarkus.book_catalog.api.BookInfoApi;
import fun.is.quarkus.book_catalog.collaborators.openlibrary.api.OpenLibraryApi;
import fun.is.quarkus.book_catalog.collaborators.openlibrary.dto.OpenLibraryBookDto;
import fun.is.quarkus.book_catalog.collaborators.stargate.api.DocumentsApi;
import fun.is.quarkus.book_catalog.dto.BookInfoDto;
import fun.is.quarkus.book_catalog.mapper.BookInfoMapper;
import fun.is.quarkus.book_catalog.model.BookById;
import fun.is.quarkus.book_catalog.model.Books;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class BookInfoService implements BookInfoApi {

    @ConfigProperty(name = "stargate.book-catalog.namespace")
    String cassNamespace;

    @ConfigProperty(name = "stargate.book-catalog.collection")
    String cassCollection;

    @Inject
    StargateAuthToken authToken;

    @RestClient
    @Inject
    DocumentsApi stargateDoc;

    @RestClient
    @Inject
    OpenLibraryApi openLibrary;

    @Inject
    BookInfoMapper bookMapper;

    @Override
    public Uni<Response> getBookById(String catalogId) {
        return stargateDoc.getDocById(authToken.getAuthToken(), cassNamespace, cassCollection, catalogId, null, null).ifNoItem().after(Duration.ofMillis(1000)).failWith(new Exception("Query Timeout")).onItem().transform(reply -> Response.ok(bookMapper.bookInfoToDto(reply.readEntity(BookById.class).data())).build()).onFailure().transform(fail -> new Exception(fail.getMessage()));
    }

    @Override
    public Uni<Response> getBookByIsbn(String isbn) {

        String isbnType = "isbn13";
        if (isbn.length() == 10) {
            isbnType = "isbn10";
        }
        if (isbn.length() == 13) {
            isbnType = "isbn13";
        }

        String isbnQuery = "{\"identifiers." + isbnType + "List.[*]." + isbnType + "\":{\"$eq\":\"" + isbn + "\"}}";

        return processQuery(isbnQuery, 1);
    }

    @Override
    public Uni<Response> getBooksByAuthor(String author, Integer numResults) {
           
        String authorQuery = "{\"authors.[*].name\":{\"$eq\":\"" + author + "\"}}";

        return processQuery(authorQuery, numResults);
    }

    @Override
    public Uni<Response> getOpenLibraryBookByIsbn(String isbn) {
        return openLibrary.getBookInfo(isbn, "json", "data").ifNoItem().after(Duration.ofMillis(1000)).failWith(new Exception("Query Timeout")).onItem().transform(reply -> Response.ok(bookMapper.OpenLibraryBookDtoToBookInfoDto(reply.readEntity(OpenLibraryBookDto.class))).build()).onFailure().transform(fail -> new Exception(fail.getMessage()));
    }

    @Override
    public Uni<Response> saveBookInfo(BookInfoDto dto) {
        return stargateDoc.replaceDoc(authToken.getAuthToken(), cassNamespace, cassCollection, dto.catalogId(), bookMapper.dtoToBookInfo(dto)).onItem().transform(reply -> Response.ok(reply.readEntity(Object.class)).build());
    }

    private Uni<Response> processQuery(String query, Integer numResults) {
        return stargateDoc.searchDoc(authToken.getAuthToken(), cassNamespace, cassCollection, query, null, numResults, null, null).ifNoItem().after(Duration.ofMillis(1000)).failWith(new Exception("Query Timeout")).onItem().transform(reply -> Response.ok(bookMapper.bookInfosToDtos(reply.readEntity(Books.class).books())).build()).onFailure().transform(fail -> new Exception(fail.getMessage()));
    }
}