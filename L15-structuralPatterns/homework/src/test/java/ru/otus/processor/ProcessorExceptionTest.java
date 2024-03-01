package ru.otus.processor;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.homework.ProcessorEvenSecEx;

public class ProcessorExceptionTest {

    static Message message;

    @BeforeAll
    public static void beforeAll() {
        List<String> data = List.of("data1");
        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(data);

        message = new Message.Builder(1000L)
                .field1("field1")
                .field13(objectForMessage)
                .build();
    }

    @Test
    void testProcessorExceptionEvenSecond() {
        Processor processor = new ProcessorEvenSecEx(() -> LocalDateTime.of(2024, 3, 1, 0, 0, 22));
        Assertions.assertThrows(IllegalStateException.class, () -> {
            processor.process(message);
        });
    }

    @Test
    void testProcessorExceptionOddSecond() {
        Processor processor = new ProcessorEvenSecEx(() -> LocalDateTime.of(2024, 3, 1, 0, 0, 11));
        Assertions.assertDoesNotThrow(() -> {
            processor.process(message);
        });
    }
}
