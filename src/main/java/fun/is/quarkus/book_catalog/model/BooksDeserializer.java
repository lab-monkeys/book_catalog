package fun.is.quarkus.book_catalog.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
        Log.debug(data);
        JsonNode books = data.get("data");
        Log.debug(books);
           
        int resultSize = data.size();
        Log.debug("Result Size: " + resultSize);
        List<BookInfo> results = new ArrayList<BookInfo>();
        Iterator<String> fields = books.fieldNames();
        while (fields.hasNext()) {
            BookInfo bookInfo = objectMapper.treeToValue(books.get(fields.next()), BookInfo.class);
            Log.debug(bookInfo);
            results.add(bookInfo);
        }
        return new Books(results);
    }
}