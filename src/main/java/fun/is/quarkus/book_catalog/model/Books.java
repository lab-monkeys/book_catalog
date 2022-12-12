package fun.is.quarkus.book_catalog.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = BooksDeserializer.class)
public record Books(List<BookInfo> books) {}