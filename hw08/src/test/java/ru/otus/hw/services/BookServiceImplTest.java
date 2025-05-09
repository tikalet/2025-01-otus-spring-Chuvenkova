package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.converters.GenreConverter;
import ru.otus.hw.exceptions.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataMongoTest
@Import({BookServiceImpl.class, AuthorServiceImpl.class, GenreServiceImpl.class,
        BookConverter.class, AuthorConverter.class, GenreConverter.class})
public class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private AuthorServiceImpl authorService;

    @Autowired
    private GenreServiceImpl genreService;

    @Autowired
    private BookConverter bookConverter;

    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        assertThatCode(() -> bookService.findById("1")).doesNotThrowAnyExceptionExcept(EntityNotFoundException.class);

        var bookList = bookService.findAll();
        assertThat(bookList).isNotNull().isNotEmpty();

        var expectedBook = bookService.findById(bookList.get(0).getId());
        assertThat(expectedBook).isNotNull();
        assertThatCode(() -> bookConverter.bookToString(expectedBook)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен загружать все книги")
    @Test
    void shouldReturnCorrectAllBook() {
        var expectedBooks = bookService.findAll();
        assertThat(expectedBooks).isNotNull().isNotEmpty();
        assertThatCode(() -> expectedBooks.forEach(book -> bookConverter.bookToString(book)))
                .doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен добавить книгу")
    @Test
    void shouldInsertBook() {
        assertThatCode(() -> bookService.insert("new_book_title", "1", "1"))
                .doesNotThrowAnyExceptionExcept(EntityNotFoundException.class);

        var authorList = authorService.findAll();
        var genreList = genreService.findAll();

        assertThat(authorList).isNotNull().isNotEmpty();
        assertThat(genreList).isNotNull().isNotEmpty();

        var expectedBook = bookService.insert("new_book_title", authorList.get(0).getId(), genreList.get(0).getId());
        assertThat(expectedBook).isNotNull();
        assertThatCode(() -> bookConverter.bookToString(expectedBook)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен обновить книгу")
    @Test
    void shouldUpdateBook() {
        assertThatCode(() -> bookService.update("1", "update_book_title", "1", "1"))
                .doesNotThrowAnyExceptionExcept(EntityNotFoundException.class);

        var authorList = authorService.findAll();
        var genreList = genreService.findAll();
        var bookList = bookService.findAll();

        assertThat(bookList).isNotNull().isNotEmpty();
        assertThat(authorList).isNotNull().isNotEmpty();
        assertThat(genreList).isNotNull().isNotEmpty();

        var expectedBook = bookService.update(bookList.get(0).getId(), "update_book_title",
                authorList.get(0).getId(), genreList.get(0).getId());
        assertThat(expectedBook).isNotNull();
        assertThatCode(() -> bookConverter.bookToString(expectedBook)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен удалить книгу")
    @Test
    void shouldDeleteBook() {
        assertThatCode(() -> bookService.deleteById("1")).doesNotThrowAnyExceptionExcept();

        var bookList = bookService.findAll();
        assertThat(bookList).isNotNull().isNotEmpty();
        assertThatCode(() -> bookService.deleteById(bookList.get(0).getId())).doesNotThrowAnyExceptionExcept();
    }
}
