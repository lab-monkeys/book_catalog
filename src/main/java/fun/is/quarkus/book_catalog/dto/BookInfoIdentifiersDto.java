package fun.is.quarkus.book_catalog.dto;

import java.util.List;

public record BookInfoIdentifiersDto(
    List<String> isbn10,
    List<String> isbn13
) {}