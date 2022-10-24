package fun.is.quarkus.book_catalog.collaborators.openlibrary.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenLibraryBookIdentifiersDto(
    @JsonProperty("isbn_13")
    List<String> isbn13,
    List<String> amazon,
    @JsonProperty("isbn_10")
    List<String> isbn10,
    List<String> openlibrary
){}
