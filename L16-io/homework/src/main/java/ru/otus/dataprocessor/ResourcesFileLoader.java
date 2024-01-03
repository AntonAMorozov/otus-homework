package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;
import java.util.List;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private final ObjectMapper objectMapper;
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.objectMapper = JsonMapper.builder().build();
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        try {
            return this.objectMapper.readValue(
                    this.getClass().getResourceAsStream("/" + fileName), new TypeReference<>() {});
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
