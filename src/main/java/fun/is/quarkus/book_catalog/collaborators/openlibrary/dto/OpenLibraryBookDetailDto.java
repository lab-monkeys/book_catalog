package fun.is.quarkus.book_catalog.collaborators.openlibrary.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenLibraryBookDetailDto(
    List<OpenLibraryBookPublisherDto> publishers, 
    OpenLibraryBookIdentifiersDto identifiers, 
    String title, 
    String url, 
    String notes, 
    @JsonProperty("number_of_pages")
    Long numberOfPages, 
    OpenLibraryBookCoverUrlDto cover, 
    List<OpenLibraryBookSubjectsDto> subjects, 
    @JsonProperty("publish_date") 
    String publishDate, 
    String key, 
    List<OpenLibraryBookAuthorDto> authors
    ) {}
