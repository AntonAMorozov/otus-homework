package ru.otus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.Listener;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.listener.homework.HistoryReader;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ProcessorEvenSecEx;
import ru.otus.processor.homework.ProcessorSwap11And12Fields;

@SuppressWarnings("java:S106")
public class HomeWork {

    public static void main(String[] args) {
        List<String> data = new ArrayList<>(List.of("data1", "data2", "data3"));
        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(data);

        Message message = new Message.Builder(2L)
                .field11("field11")
                .field12("field12")
                .field13(objectForMessage)
                .build();

        List<Processor> processors =
                List.of(new ProcessorSwap11And12Fields(), new ProcessorEvenSecEx(LocalDateTime::now));

        ComplexProcessor complexProcessor = new ComplexProcessor(processors, System.out::println);
        HistoryReader historyListener = new HistoryListener();
        complexProcessor.addListener((Listener) historyListener);

        System.out.println("message:" + message);

        Message result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        System.out.println("Change field 13 in result");
        result.getField13().setData(new ArrayList<>());

        System.out.println("Changed result:" + result);

        System.out.println("History:" + historyListener.findMessageById(message.getId()));
    }
}
