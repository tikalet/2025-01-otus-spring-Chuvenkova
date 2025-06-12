package ru.otus.hw.services.relation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.relation.Book;
import ru.otus.hw.models.relation.Comment;
import ru.otus.hw.repositories.relation.BookRepository;
import ru.otus.hw.repositories.relation.CommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public List<Comment> findByBookId(long bookId) {
        getBook(bookId);
        return commentRepository.findByBookId(bookId);
    }

    private Book getBook(long bookId) {
        return bookRepository.findById(bookId).
                orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
    }
}
