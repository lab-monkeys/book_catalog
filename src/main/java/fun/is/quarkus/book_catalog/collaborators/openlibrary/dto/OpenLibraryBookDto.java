package fun.is.quarkus.book_catalog.collaborators.openlibrary.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = OpenLibraryBookDeserializer.class)
public record OpenLibraryBookDto(String isbn, OpenLibraryBookDetailDto details) {}
