package ru.otus.hw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.CommentConverter;

@SpringBootTest
@Transactional(propagation = Propagation.NEVER)
public class CommentServiceImplTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private CommentConverter commentConverter;

  /*  @DisplayName("должен загружать комментарий по id")
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
    void shouldInsertComment() {
        var expectedComment = commentService.insert(1, "new_comment_text");
        assertThat(expectedComment).isNotNull();
        assertThatCode(() -> commentConverter.commentToString(expectedComment)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен обновить комментарий для книги")
    @Test
    void shouldUpdateComment() {
        var expectedComment = commentService.update(1, "updated_comment_text");
        assertThat(expectedComment).isNotNull();
        assertThatCode(() -> commentConverter.commentToString(expectedComment)).doesNotThrowAnyExceptionExcept();
    }

    @DisplayName("должен удалить комментарий")
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void shouldDeleteComment() {
        assertThatCode(() -> commentService.deleteById(1)).doesNotThrowAnyExceptionExcept();
    }*/
}
