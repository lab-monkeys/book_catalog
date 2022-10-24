package fun.is.quarkus.book_catalog.model;

import java.util.List;

public record BookInfoIdentifiers(
    List<BookInfoISBN10> isbn10List,
    List<BookInfoISBN13> isbn13List
) {}
