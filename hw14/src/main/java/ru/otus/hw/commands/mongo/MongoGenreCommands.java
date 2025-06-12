package ru.otus.hw.commands.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.mongo.MongoGenreConverter;
import ru.otus.hw.services.mongo.MongoGenreService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent(value = "Mongo genre commands")
public class MongoGenreCommands {

    private final MongoGenreService mongoGenreService;

    private final MongoGenreConverter mongoGenreConverter;

    @ShellMethod(value = "Find all genres", key = "mo-ag")
    public String findAllGenres() {
        return mongoGenreService.findAll().stream()
                .map(mongoGenreConverter::genreToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }
}
