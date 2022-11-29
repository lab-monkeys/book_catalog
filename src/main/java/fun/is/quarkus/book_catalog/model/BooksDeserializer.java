package fun.is.quarkus.book_catalog.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.logging.Log;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BooksDeserializer extends JsonDeserializer<Books> {

    @Override
    public Books deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode data = p.getCodec().readTree(p);
        Log.info(data);
        JsonNode books = data.get("data");
        Log.info(books);
        
        int resultSize = data.size();
        Log.info("Result Size: " + resultSize);
        int index = 0;
        List<BookInfo> results = new ArrayList<BookInfo>();
        while (index < resultSize) {
            Log.info(data.get(0));
            BookInfo bookInfo = objectMapper.treeToValue(data.get(index), BookInfo.class);
            Log.info(bookInfo);
            results.add(bookInfo);
            index ++;
        }
        return new Books(results);
    }
}
