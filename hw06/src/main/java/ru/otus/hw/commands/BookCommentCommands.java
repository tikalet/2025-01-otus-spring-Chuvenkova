package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.converters.BookCommentConverter;
import ru.otus.hw.services.BookCommentService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent(value = "book comment commands")
public class BookCommentCommands {

    private final BookCommentService bookCommentService;

    private final BookCommentConverter bookCommentConverter;

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findBookCommentById(long id) {
        return bookCommentConverter.bookCommentToString(bookCommentService.findById(id));
    }

    @ShellMethod(value = "Find comment by book id", key = "cbbid")
    public String findBookCommentByBookId(long bookId) {
        return bookCommentService.findByBookId(bookId).stream()
                .map(bookCommentConverter::bookCommentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Insert comment", key = "cins")
    public String insertBook(String comment, long bookId) {
        var savedBook = bookCommentService.insert(comment, bookId);
        return bookCommentConverter.bookCommentToString(savedBook);
    }

    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateBook(long id, long bookId, String comment) {
        var savedBook = bookCommentService.update(id, comment, bookId);
        return bookCommentConverter.bookCommentToString(savedBook);
    }

    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public void deleteBook(long id) {
        bookCommentService.deleteById(id);
    }
}
