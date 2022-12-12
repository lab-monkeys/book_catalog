package fun.is.quarkus.book_catalog.collaborators.openlibrary.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenLibraryBookAuthorDto (String url, String name) {}
