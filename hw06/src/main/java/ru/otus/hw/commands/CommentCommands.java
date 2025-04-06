package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.services.CommentService;

import java.util.stream.Collectors;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@RequiredArgsConstructor
@ShellComponent(value = "book comment commands")
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findBookCommentById(long id) {
        return commentService.findById(id)
                .map(commentConverter::bookCommentToString)
                .orElse("Comment with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Find comment by book id", key = "cbbid")
    public String findBookCommentByBookId(long bookId) {
        return commentService.findByBookId(bookId).stream()
                .map(commentConverter::bookCommentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertBook(long bookId, String comment) {
        var savedBook = commentService.insert(comment, bookId);
        return commentConverter.bookCommentToString(savedBook);
    }

    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateBook(long id, long bookId, String comment) {
        var savedBook = commentService.update(id, comment, bookId);
        return commentConverter.bookCommentToString(savedBook);
    }

    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteBook(long id) {
        commentService.deleteById(id);
    }
}
