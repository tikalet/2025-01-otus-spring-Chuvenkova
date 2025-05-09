package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с комментариями для книги")
@DataMongoTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("должен загружать комментарий по id книги")
    @Test
    void shouldReturnCorrectCommentByBookId() {
        List<Comment> emptyCommentList = commentRepository.findByBook("not_exist");
        assertThat(emptyCommentList).isEmpty();

        List<Book> bookList = bookRepository.findAll();
        List<Comment> actualComments = commentRepository.findByBook(bookList.get(0).getId());

        assertThat(actualComments).isNotNull().isNotEmpty()
                .allMatch(comment -> comment.getCommentText() != null
                        && !comment.getCommentText().isEmpty());
    }

}
