package fun.is.quarkus.book_catalog.dto;

import java.util.List;

public record BookInfoDto (
    String catalogId,
    String title,
    String openLibraryUrl,
    Long numberOfPages,
    String coverImageUrl,
    String publishDate,
    boolean inCatalog,
    List<String> isbns,
    List<BookInfoAuthorDto> authors
) {}
