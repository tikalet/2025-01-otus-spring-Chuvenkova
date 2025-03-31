package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.BookComment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями для книги")
@DataJpaTest
@Import({JpaBookCommentRepository.class})
public class JpaBookCommentRepositoryTest {

    private static final long FIRST_ID = 1;

    private static final int COMMENTS_SIZE = 3;

    @Autowired
    private JpaBookCommentRepository repositoryJpa;

    @Autowired
    private TestEntityManager entityManager;

    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnCorrectCommentById() {
        var actualComment = repositoryJpa.findById(FIRST_ID);
        var expectedComment = entityManager.find(BookComment.class, FIRST_ID);
        assertThat(actualComment).isPresent()
                .get()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен загружать комментарий по id книги")
    @Test
    void shouldReturnCorrectCommentByBookId() {
        var actualComments = repositoryJpa.findByBookId(FIRST_ID);

        assertThat(actualComments).isNotNull().hasSize(COMMENTS_SIZE)
                .allMatch(bookComment -> bookComment.getCommentText() != null
                        && !bookComment.getCommentText().isEmpty());

        actualComments.forEach(System.out::println);
    }


    @DisplayName("должен сохранять новый комментарий")
    @Test
    void shouldSaveNewBookComment() {
        var book = entityManager.find(Book.class, FIRST_ID);

        var expectedComment = new BookComment(0, "Comment_text_1_158521", book);
        var returnedComment = repositoryJpa.save(expectedComment);

        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

        assertThat(entityManager.find(BookComment.class, returnedComment.getId()))
                .isNotNull()
                .isEqualTo(returnedComment);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var book = entityManager.find(Book.class, FIRST_ID);

        var expectedComment = new BookComment(1, "Comment_text_1_158521", book);

        assertThat(entityManager.find(BookComment.class, expectedComment.getId())).isNotNull()
                .isNotEqualTo(expectedComment);

        var returnedComment = repositoryJpa.save(expectedComment);
        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

        assertThat(entityManager.find(BookComment.class, returnedComment.getId())).isEqualTo(returnedComment);
    }

    @DisplayName("должен удалять комментарий по id ")
    @Test
    void shouldDeleteBookComment() {
        assertThat(entityManager.find(BookComment.class, 1)).isNotNull();
        repositoryJpa.deleteById(1L);
        assertThat(entityManager.find(BookComment.class, 1)).isNull();
    }
}
