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
@Import({CommentServiceImpl.class})
@Transactional(propagation = Propagation.NEVER)
public class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;

    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnCorrectCommentById() {
        assertThatCode(() -> commentService.findById(1)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен загружать комментарий для книги")
    @Test
    void shouldReturnCorrectCommentByBookId() {
        assertThatCode(() -> commentService.findByBookId(1)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен добавить комментарий для книги")
    @Test
    void shouldInsertComment() {
        assertThatCode(() -> commentService.insert(1, "new_comment_text")).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен обновить комментарий для книги")
    @Test
    void shouldUpdateComment() {
        assertThatCode(() -> commentService.update(1, "updated_comment_text")).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен удалить комментарий")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteComment() {
        assertThatCode(() -> commentService.deleteById(1)).doesNotThrowAnyExceptionExcept();
    }
}
