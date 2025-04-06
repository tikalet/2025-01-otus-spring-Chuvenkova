package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.CommentConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@SpringBootTest
@Transactional(propagation = Propagation.NEVER)
public class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private CommentConverter commentConverter;

    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnCorrectCommentById() {
        var expectedComment = commentService.findById(1);
        assertThat(expectedComment).isPresent();
        assertThatCode(() -> commentConverter.commentToString(expectedComment.get())).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен загружать комментарий для книги")
    @Test
    void shouldReturnCorrectCommentByBookId() {
        var expectedComments = commentService.findByBookId(1);
        assertThat(expectedComments).isNotNull().isNotEmpty();
        assertThatCode(() -> expectedComments.forEach(comment -> commentConverter.commentToString(comment)))
                .doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен добавить комментарий для книги")
    @Test
    void shouldInsertCommentForBookId() {
        var expectedComment = commentService.insert(3, "new_comment_text");
        assertThat(expectedComment).isNotNull();
        assertThatCode(() -> commentConverter.commentToString(expectedComment)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен обновить комментарий для книги")
    @Test
    void shouldUpdateCommentForBookId() {
        var expectedComment = commentService.update(1, "updated_comment_text", 1);
        assertThat(expectedComment).isNotNull();
        assertThatCode(() -> commentConverter.commentToString(expectedComment)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен удалить комментарий")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteCommentForBookId() {
        assertThatCode(() -> commentService.deleteById(1)).doesNotThrowAnyExceptionExcept();
    }
}
