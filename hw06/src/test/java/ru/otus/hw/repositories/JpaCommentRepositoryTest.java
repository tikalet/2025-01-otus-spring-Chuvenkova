package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями для книги")
@DataJpaTest
@Import({JpaCommentRepository.class})
public class JpaCommentRepositoryTest {

    private static final long FIRST_ID = 1;

    @Autowired
    private JpaCommentRepository repositoryJpa;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnCorrectCommentById() {
        var actualComment = repositoryJpa.findById(FIRST_ID);
        var expectedComment = entityManager.find(Comment.class, FIRST_ID);
        assertThat(actualComment).isPresent()
                .get()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен загружать комментарий по id книги")
    @Test
    void shouldReturnCorrectCommentByBookId() {
        var actualComments = repositoryJpa.findByBookId(FIRST_ID);

        assertThat(actualComments).isNotNull()
                .allMatch(comment -> comment.getCommentText() != null
                        && !comment.getCommentText().isEmpty());

        actualComments.forEach(System.out::println);
    }

    @DisplayName("должен сохранять новый комментарий")
    @Test
    void shouldSaveNewComment() {
        var book = entityManager.find(Book.class, FIRST_ID);

        var expectedComment = new Comment(0, "Comment_text_1_158521", book);
        var returnedComment = repositoryJpa.save(expectedComment);

        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

        assertThat(entityManager.find(Comment.class, returnedComment.getId()))
                .isNotNull()
                .isEqualTo(returnedComment);
    }

    @DisplayName("должен сохранять измененный комментарий")
    @Test
    void shouldSaveUpdatedComment() {
        var book = entityManager.find(Book.class, FIRST_ID);

        var expectedComment = new Comment(1, "Comment_text_1_158521", book);

        assertThat(entityManager.find(Comment.class, expectedComment.getId())).isNotNull()
                .isNotEqualTo(expectedComment);

        var returnedComment = repositoryJpa.save(expectedComment);
        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

        assertThat(entityManager.find(Comment.class, returnedComment.getId())).isEqualTo(returnedComment);
    }

    @DisplayName("должен удалять комментарий по id")
    @Test
    void shouldDeleteComment() {
        assertThat(entityManager.find(Comment.class, 1)).isNotNull();
        repositoryJpa.deleteById(1L);
        assertThat(entityManager.find(Comment.class, 1)).isNull();
    }
}
