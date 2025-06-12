package ru.otus.hw.commands.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.mongo.MongoBookConverter;
import ru.otus.hw.services.mongo.MongoBookService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent(value = "Mongo book commands")
public class MongoBookCommands {

    private final MongoBookService mongoBookService;

    private final MongoBookConverter mongoBookConverter;

    @ShellMethod(value = "Find all books", key = "mo-ab")
    public String findAllBooks() {
        return mongoBookService.findAll().stream()
                .map(mongoBookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

}
