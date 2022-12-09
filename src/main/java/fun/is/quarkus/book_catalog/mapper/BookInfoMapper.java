package fun.is.quarkus.book_catalog.mapper;

import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
    @Mapping(target = "catalogId", expression = "java(bookInfo.details().identifiers().openlibrary().get(0))")
    BookInfoDto OpenLibraryBookDtoToBookInfoDto(OpenLibraryBookDto bookInfo);

    BookInfo dtoToBookInfo(BookInfoDto dto);

    BookInfoDto bookInfoToDto(BookInfo book);

    List<BookInfoDto> bookInfosToDtos(List<BookInfo> books);

    BookInfoAuthor dtoToBookInfoAuthor(BookInfoAuthorDto dto);

    BookInfoAuthorDto bookInfoAuthorToDto(BookInfoAuthor author);

    default BookInfoIdentifiers dtoToBookInfoIdentifiers(BookInfoIdentifiersDto dto){
        List<BookInfoISBN10> isbn10List = new ArrayList<BookInfoISBN10>();
        List<BookInfoISBN13> isbn13List = new ArrayList<BookInfoISBN13>();
        if (dto.isbn10() != null) {
        for (String isbn : dto.isbn10()) {
            BookInfoISBN10 isbn10 = new BookInfoISBN10(isbn);
            isbn10List.add(isbn10);
        }}
        if (dto.isbn13() != null) {
        for (String isbn : dto.isbn13()) {
            BookInfoISBN13 isbn13 = new BookInfoISBN13(isbn);
            isbn13List.add(isbn13);
        }}
        return new BookInfoIdentifiers(isbn10List, isbn13List);
    }

    default BookInfoIdentifiersDto bookInfoIdentifiersToDto(BookInfoIdentifiers identifiers) {
        List<String> isbn10 = new ArrayList<String>();
        List<String> isbn13 = new ArrayList<String>();

        if (identifiers.isbn10List() != null) {
        for (BookInfoISBN10 isbn : identifiers.isbn10List()) {
            isbn10.add(isbn.isbn10());
        }}
        if (identifiers.isbn13List() != null) {
        for (BookInfoISBN13 isbn : identifiers.isbn13List()) {
            isbn13.add(isbn.isbn13());
        }}
        return new BookInfoIdentifiersDto(isbn10, isbn13);

    }
}
