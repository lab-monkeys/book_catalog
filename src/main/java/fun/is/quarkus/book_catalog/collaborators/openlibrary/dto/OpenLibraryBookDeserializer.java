package fun.is.quarkus.book_catalog.collaborators.openlibrary.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenLibraryBookDeserializer extends JsonDeserializer<OpenLibraryBookDto> {

    @Override
    public OpenLibraryBookDto deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String isbn = p.nextFieldName();
        JsonNode node = p.getCodec().readTree(p);
        OpenLibraryBookDto book = new OpenLibraryBookDto(isbn, objectMapper.treeToValue(node.get(isbn), OpenLibraryBookDetailDto.class));
        return book;
    }
}
