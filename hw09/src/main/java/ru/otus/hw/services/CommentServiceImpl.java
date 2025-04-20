package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Book;
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
    public CommentDto findById(long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.map(CommentDto::fromModel).orElseThrow(()
                -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
    }

    @Override
    public List<CommentDto> findByBookId(long bookId) {
        getBook(bookId);
        return commentRepository.findByBookId(bookId).stream()
                .map(CommentDto::fromModel).toList();
    }

    @Override
    @Transactional
    public CommentDto insert(long bookId, String commentText) {
        var book = getBook(bookId);
        var comment = new Comment(0, commentText, book);
        comment = commentRepository.save(comment);
        return CommentDto.fromModel(comment);
    }

    @Override
    @Transactional
    public CommentDto update(long id, String commentText) {
        var comment = commentRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Comment with id %d not found".formatted(id)));
        comment.setCommentText(commentText);
        comment = commentRepository.save(comment);
        return CommentDto.fromModel(comment);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

    private Book getBook(long bookId) {
        return bookRepository.findById(bookId).
                orElseThrow(() -> new EntityNotFoundException("Book with id %d not found".formatted(bookId)));
    }
}
