package ru.otus.hw.commands.relation;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.relation.CommentConverter;
import ru.otus.hw.services.relation.CommentService;

import java.util.stream.Collectors;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RequiredArgsConstructor
@ShellComponent(value = "book comment commands")
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;


    @ShellMethod(value = "Find comment by book id", key = "cbbid")
    public String findCommentByBookId(long bookId) {
        return commentService.findByBookId(bookId).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

}
