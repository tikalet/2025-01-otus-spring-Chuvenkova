package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.BookComment;
import ru.otus.hw.repositories.BookCommentRepository;
import ru.otus.hw.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookCommentServiceImpl implements BookCommentService {

    private final BookCommentRepository bookCommentRepository;

    private final BookRepository bookRepository;

    @Override
    public Optional<BookComment> findById(long id) {
        return bookCommentRepository.findById(id);
    }

    @Override
    public List<BookComment> findByBookId(long bookId) {
        bookRepository.findById(bookId).
                orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
        return bookCommentRepository.findByBookId(bookId);
    }

    @Override
    @Transactional
    public BookComment insert(String comment, long bookId) {
        return save(0, comment, bookId);
    }

    @Override
    @Transactional
    public BookComment update(long id, String comment, long bookId) {
        return save(id, comment, bookId);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookCommentRepository.deleteById(id);
    }

    private BookComment save(long id, String comment, long bookId) {
        var book = bookRepository.findById(bookId).
                orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));

        var bookComment = new BookComment(id, comment, book);
        return bookCommentRepository.save(bookComment);
    }
}
