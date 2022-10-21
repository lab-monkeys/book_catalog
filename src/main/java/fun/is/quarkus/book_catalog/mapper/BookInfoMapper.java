package fun.is.quarkus.book_catalog.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import fun.is.quarkus.book_catalog.collaborators.openlibrary.dto.OpenLibraryBookDto;
import fun.is.quarkus.book_catalog.dto.BookInfoDto;

@Mapper(componentModel = "cdi")
public interface BookInfoMapper {

    @Mapping(target = "openLibraryUrl", source = "url")
    @Mapping(target = "name", source = "name")
    AuthorDTO authorOlToAuthorDTO(AuthorOL author);

    List<AuthorDTO> authorsOlToAuthorDTOs(List<AuthorOL> authors);

    @Mapping(source = "details.title", target = "title")
    @Mapping(source = "details.url", target = "openLibraryUrl")
    @Mapping(source = "details.numberOfPages", target = "numberOfPages")
    @Mapping(source = "details.cover.small", target = "coverImageUrl")
    @Mapping(source = "details.publishDate", target = "publishDate")
    @Mapping(source = "details.authors", target = "authors")
    @Mapping(target = "inCatalog", ignore = true)
    @Mapping(target = "catalogId", ignore = true)
    @Mapping(target = "isbns", ignore = true)
    BookInfoDto bookInfoOlToBookInfoDTO(OpenLibraryBookDto bookInfo);

    @AfterMapping
    default void bookInfoOlToBookInfoDtoCustom(OpenLibraryBookDto bookInfo, @MappingTarget BookInfoDto book) {
        BookDetailDto details = bookInfo.details();
        List<String> isbns = new ArrayList<String>();
        List<String> isbn13 = bookInfo.details().identifiers().isbn13();
        List<String> isbn10 = details.getIdentifiers().getIsbn10();
        if (isbn13 != null) {
            for (String isbn : isbn13) {
                isbns.add(isbn);
            }
        }
        if (isbn10 != null) {
            for (String isbn : isbn10) {
                isbns.add(isbn);
            }
        }
        book.setIsbns(isbns);
        book.setCatalogId(details.getIdentifiers().getOpenlibrary().get(0));
    }
}
