package ru.otus.hw.commands.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.mongo.MongoCommentConverter;
import ru.otus.hw.services.mongo.MongoCommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent(value = "Mongo book comment commands")
public class MongoCommentCommands {

    private final MongoCommentService mongoCommentService;

    private final MongoCommentConverter mongoCommentConverter;

    @ShellMethod(value = "Find comment by book id", key = "mo-cbbid")
    public String findCommentByBookId(String bookId) {
        return mongoCommentService.findByBookId(bookId).stream()
                .map(mongoCommentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
    
}
