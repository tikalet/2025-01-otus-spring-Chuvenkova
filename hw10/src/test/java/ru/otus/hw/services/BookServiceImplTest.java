package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.mapper.AuthorMapper;
import ru.otus.hw.mapper.BookMapper;
import ru.otus.hw.mapper.GenreMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DataJpaTest
@Import({BookServiceImpl.class, BookMapper.class, AuthorMapper.class, GenreMapper.class})
@Transactional(propagation = Propagation.NEVER)
public class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;

    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnCorrectBookById() {
        assertThatCode(() -> bookService.findById(1)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен загружать все книги")
    @Test
    void shouldReturnCorrectAllBook() {
        assertThatCode(() -> bookService.findAll()).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен добавить книгу")
    @Test
    void shouldCreateBook() {
        BookCreateDto bookCreateDto = new BookCreateDto("new_book_title", 1L, 1L);
        assertThatCode(() -> bookService.create(bookCreateDto)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен обновить книгу")
    @Test
    void shouldUpdateBook() {
        BookUpdateDto bookUpdateDto = new BookUpdateDto(1L, "update_book_title", 1L, 1L);
        assertThatCode(() -> bookService.update(bookUpdateDto)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен удалить книгу")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteBook() {
        assertThatCode(() -> bookService.deleteById(1)).doesNotThrowAnyExceptionExcept();
    }
}
