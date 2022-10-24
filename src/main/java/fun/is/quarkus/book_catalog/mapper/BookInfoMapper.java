package fun.is.quarkus.book_catalog.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import fun.is.quarkus.book_catalog.collaborators.openlibrary.dto.OpenLibraryBookAuthorDto;
import fun.is.quarkus.book_catalog.collaborators.openlibrary.dto.OpenLibraryBookDto;
import fun.is.quarkus.book_catalog.dto.BookInfoIdentifiersDto;
import fun.is.quarkus.book_catalog.dto.BookInfoAuthorDto;
import fun.is.quarkus.book_catalog.dto.BookInfoDto;
import fun.is.quarkus.book_catalog.model.BookInfo;
import fun.is.quarkus.book_catalog.model.BookInfoAuthor;
import fun.is.quarkus.book_catalog.model.BookInfoISBN10;
import fun.is.quarkus.book_catalog.model.BookInfoISBN13;
import fun.is.quarkus.book_catalog.model.BookInfoIdentifiers;

@Mapper(componentModel = "cdi")
public interface BookInfoMapper {

    @Mapping(target = "openLibraryUrl", source = "url")
    @Mapping(target = "name", source = "name")
    BookInfoAuthorDto openLibraryAuthorToDto(OpenLibraryBookAuthorDto author);

    List<BookInfoAuthorDto> openLibraryAuthorsToDtos(List<OpenLibraryBookAuthorDto> authors);

    @Mapping(source = "details.title", target = "title")
    @Mapping(source = "details.url", target = "openLibraryUrl")
    @Mapping(source = "details.numberOfPages", target = "numberOfPages")
    @Mapping(source = "details.cover.small", target = "coverImageUrl")
    @Mapping(source = "details.publishDate", target = "publishDate")
    @Mapping(source = "details.authors", target = "authors")
    @Mapping(source = "details.identifiers", target = "identifiers")
    @Mapping(target = "inCatalog", ignore = true)
    @Mapping(target = "catalogId", ignore = true)
    BookInfoDto OpenLibraryBookDtoToBookInfoDto(OpenLibraryBookDto bookInfo);

    BookInfo dtoToBookInfo(BookInfoDto dto);

    BookInfoDto bookInfoToDto(BookInfo book);

    BookInfoAuthor dtoToBookInfoAuthor(BookInfoAuthorDto dto);

    BookInfoAuthorDto bookInfoAuthorToDto(BookInfoAuthor author);

    @Mapping(target = "isbn10List", ignore = true)
    @Mapping(target = "isbn13List", ignore = true)
    BookInfoIdentifiers dtoToBookInfoIdentifiers(BookInfoIdentifiersDto dto);

    @AfterMapping
    default void dtoToBookInfoIdentifiersCustom(BookInfoIdentifiersDto dto, @MappingTarget BookInfoIdentifiers identifiers) {

        List<BookInfoISBN10> isbn10List = new ArrayList<BookInfoISBN10>();
        List<BookInfoISBN13> isbn13List = new ArrayList<BookInfoISBN13>();
        for (String isbn : dto.isbn10()) {
            BookInfoISBN10 isbn10 = new BookInfoISBN10(isbn);
            isbn10List.add(isbn10);
        }
        for (String isbn : dto.isbn13()) {
            BookInfoISBN13 isbn13 = new BookInfoISBN13(isbn);
            isbn13List.add(isbn13);
        }
        identifiers = new BookInfoIdentifiers(isbn10List, isbn13List);
    }

    @Mapping(target = "isbn10", ignore = true)
    @Mapping(target = "isbn13", ignore = true)
    BookInfoIdentifiersDto bookInfoIdentifiersToDto(BookInfoIdentifiers identifiers);

    @AfterMapping
    default void bookInfoIdentifiersToDtoCustom(BookInfoIdentifiers identifiers, @MappingTarget BookInfoIdentifiersDto dto) {
        List<String> isbn10 = new ArrayList<String>();
        List<String> isbn13 = new ArrayList<String>();

        for (BookInfoISBN10 isbn : identifiers.isbn10List()) {
            isbn10.add(isbn.isbn10());
        }
        for (BookInfoISBN13 isbn : identifiers.isbn13List()) {
            isbn13.add(isbn.isbn13());
        }
        dto = new BookInfoIdentifiersDto(isbn10, isbn13);
    }
}
