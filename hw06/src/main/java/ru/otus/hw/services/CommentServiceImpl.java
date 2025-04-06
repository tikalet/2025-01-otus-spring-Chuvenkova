package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<Comment> findByBookId(long bookId) {
        bookRepository.findById(bookId).
                orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        return commentRepository.findByBookId(bookId);
    }

    @Override
    @Transactional
    public Comment insert(long bookId, String comment) {
        return save(0, comment, bookId);
    }

    @Override
    @Transactional
    public Comment update(long id, String comment, long bookId) {
        commentRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        return save(id, comment, bookId);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private Comment save(long id, String comment, long bookId) {
        var book = bookRepository.findById(bookId).
                orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        var bookComment = new Comment(id, comment, book);
        return commentRepository.save(bookComment);
    }
}
