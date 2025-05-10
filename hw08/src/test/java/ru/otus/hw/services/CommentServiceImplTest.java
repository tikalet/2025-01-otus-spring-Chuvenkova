package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.exceptions.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DataMongoTest
@Import({BookServiceImpl.class, CommentServiceImpl.class, CommentConverter.class})
public class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private CommentConverter commentConverter;

    @Autowired
    private BookServiceImpl bookService;

    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnCorrectCommentById() {
        assertThatCode(() -> commentService.findById("1")).doesNotThrowAnyExceptionExcept(EntityNotFoundException.class);

        var bookList = bookService.findAll();
        assertThat(bookList).isNotNull().isNotEmpty();

        var commentList = commentService.findByBookId(bookList.get(0).getId());
        assertThat(commentList).isNotNull().isNotEmpty();

        var expectedComment = commentService.findById(commentList.get(0).getId());
        assertThat(expectedComment).isPresent();
        assertThatCode(() -> commentConverter.commentToString(expectedComment.get())).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен загружать комментарий для книги")
    @Test
    void shouldReturnCorrectCommentByBookId() {
        assertThatCode(() -> commentService.findByBookId("1"))
                .doesNotThrowAnyExceptionExcept(EntityNotFoundException.class);

        var bookList = bookService.findAll();
        assertThat(bookList).isNotNull().isNotEmpty();

        var expectedComments = commentService.findByBookId(bookList.get(0).getId());
        assertThat(expectedComments).isNotNull().isNotEmpty();
        assertThatCode(() -> expectedComments.forEach(comment -> commentConverter.commentToString(comment)))
                .doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен добавить комментарий для книги")
    @Test
    void shouldInsertComment() {
        assertThatCode(() -> commentService.insert("1", "new_comment_text"))
                .doesNotThrowAnyExceptionExcept(EntityNotFoundException.class);

        var bookList = bookService.findAll();
        assertThat(bookList).isNotNull().isNotEmpty();

        var expectedComment = commentService.insert(bookList.get(0).getId(), "new_comment_text");
        assertThat(expectedComment).isNotNull();
        assertThatCode(() -> commentConverter.commentToString(expectedComment)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен обновить комментарий для книги")
    @Test
    void shouldUpdateComment() {
        assertThatCode(() ->  commentService.update("1", "updated_comment_text"))
                .doesNotThrowAnyExceptionExcept(EntityNotFoundException.class);

        var bookList = bookService.findAll();
        assertThat(bookList).isNotNull().isNotEmpty();

        var commentList = commentService.findByBookId(bookList.get(0).getId());
        assertThat(commentList).isNotNull().isNotEmpty();

        var expectedComment = commentService.update(commentList.get(0).getId(), "updated_comment_text");
        assertThat(expectedComment).isNotNull();
        assertThatCode(() -> commentConverter.commentToString(expectedComment)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен удалить комментарий")
    @Test
    void shouldDeleteComment() {
        assertThatCode(() -> commentService.deleteById("1")).doesNotThrowAnyExceptionExcept();
    }
}
