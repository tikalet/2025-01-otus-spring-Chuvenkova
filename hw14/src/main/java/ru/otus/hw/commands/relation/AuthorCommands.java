package ru.otus.hw.commands.relation;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.relation.AuthorConverter;
import ru.otus.hw.services.relation.AuthorService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent(value = "author commands")
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    @ShellMethod(value = "Find all authors", key = "aa")
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

}
