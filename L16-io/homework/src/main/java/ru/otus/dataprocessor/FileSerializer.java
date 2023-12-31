package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final ObjectMapper objectMapper;
    private final String fileName;

    public FileSerializer(String fileName) {
        this.objectMapper = JsonMapper.builder().build();
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        var file = new File(fileName);
        try {
            this.objectMapper.writeValue(file, data);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
