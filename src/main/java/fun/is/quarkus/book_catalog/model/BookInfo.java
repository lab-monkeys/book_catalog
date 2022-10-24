package fun.is.quarkus.book_catalog.model;

import java.util.List;

public record BookInfo (
    String catalogId,
    String title,
    String openLibraryUrl,
    Long numberOfPages,
    String coverImageUrl,
    String publishDate,
    boolean inCatalog,
    BookInfoIdentifiers identifiers,
    List<BookInfoAuthor> authors
) {}
