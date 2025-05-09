package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("BookRepository без EventsListener")
@DataMongoTest
public class BookRepositoryWithoutCascadeDeleteEventsListenerTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    @DisplayName("должен оставит комментарии при удалении книги")
    @Test
    void shouldDeleteCommentForBook() {
        List<Book> bookList = bookRepository.findAll();
        Book deletedBook = bookList.get(0);

        List<Comment> actualComments = commentRepository.findByBook(deletedBook.getId());
        assertThat(actualComments).isNotNull().isNotEmpty();

        bookRepository.deleteById(deletedBook.getId());

        List<Comment> deletedComments = commentRepository.findByBook(deletedBook.getId());
        assertThat(deletedComments).isNotNull().isNotEmpty();

        assertThat(actualComments.size()).isEqualTo(deletedComments.size());
    }
}
