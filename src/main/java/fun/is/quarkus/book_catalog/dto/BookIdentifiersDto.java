package fun.is.quarkus.book_catalog.dto;

public record BookIdentifiersDto(
    String isbn10,
    String isbn13,
    String openLibrary
) {}
