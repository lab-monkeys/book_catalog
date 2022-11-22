package fun.is.quarkus.book_catalog.service;

import java.time.Duration;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import fun.is.quarkus.book_catalog.api.BookInfoApi;
import fun.is.quarkus.book_catalog.collaborators.stargate.api.DocumentsApi;
import fun.is.quarkus.book_catalog.dto.BookInfoDto;
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
    DocumentsApi stargateDoc;

    @Override
    public Uni<Response> getBookById(String catalogId) {
        stargateDoc.getDocById(authToken.getAuthToken(), cassNamespace, cassCollection, catalogId, catalogId, null);
        return null;
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

        //stargateDoc.searchDoc(authToken.getAuthToken(), cassNamespace, cassCollection, isbnQuery, null, null, null, null).ifNoItem().after(Duration.ofMillis(1000)).failWith(new Exception("Query Timeout")).subscribe().with(reply -> {response=reply;}, fail -> {error = fail;});
        return stargateDoc.searchDoc(authToken.getAuthToken(), cassNamespace, cassCollection, isbnQuery, null, null, null, null).ifNoItem().after(Duration.ofMillis(1000)).failWith(new Exception("Query Timeout")).onItem().transform(reply -> reply);

    }

    @Override
    public Uni<Response> getBooksByAuthor(String author) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uni<Response> getOpenLibraryBookByIsbn(String isbn) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Uni<Response> saveBookInfo(BookInfoDto dto) {
        // TODO Auto-generated method stub
        return null;
    }


}
