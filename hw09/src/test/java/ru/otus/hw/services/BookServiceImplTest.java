package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

@DataJpaTest
@Import({BookServiceImpl.class})
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
    void shouldInsertBook() {
        assertThatCode(() -> bookService.insert("new_book_title", 1, 1)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен обновить книгу")
    @Test
    void shouldUpdateBook() {
        assertThatCode(() -> bookService.update(1, "update_book_title", 1, 1)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен удалить книгу")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteBook() {
        assertThatCode(() -> bookService.deleteById(1)).doesNotThrowAnyExceptionExcept();
    }
}
