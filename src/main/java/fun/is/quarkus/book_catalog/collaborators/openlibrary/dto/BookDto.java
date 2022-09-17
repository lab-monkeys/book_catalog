package fun.is.quarkus.book_catalog.collaborators.openlibrary.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = OpenLibraryBookDeserializer.class)
public record BookDto(String isbn, BookDetailDto details) {}

@JsonIgnoreProperties(ignoreUnknown = true)
final record BookDetailDto(
    List<PublisherDto> publishers, 
    IdentifiersDto identifiers, 
    String title, 
    String url, 
    String notes, 
    @JsonProperty("number_of_pages")
    Long numberOfPages, 
    CoverUrlDto cover, 
    List<SubjectsDto> subjects, 
    @JsonProperty("publish_date") 
    String publishDate, 
    String key, 
    List<AuthorDto> authors
    ) {}

@JsonIgnoreProperties(ignoreUnknown = true)
final record SubjectsDto (String url, String name){}

@JsonIgnoreProperties(ignoreUnknown = true)
final record PublisherDto(String name){}

@JsonIgnoreProperties(ignoreUnknown = true)
final record IdentifiersDto(
    @JsonProperty("isbn_13")
    List<String> isbn13,
    List<String> amazon,
    @JsonProperty("isbn_10")
    List<String> isbn10,
    List<String> openlibrary
){}

@JsonIgnoreProperties(ignoreUnknown = true)
final record CoverUrlDto(String small, String large, String medium){}

@JsonIgnoreProperties(ignoreUnknown = true)
final record AuthorDto (String url, String name) {}
