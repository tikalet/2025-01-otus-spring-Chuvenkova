package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> findById(long id);

    List<Comment> findByBookId(long bookId);

    Comment insert(long bookId, String comment);

    Comment update(long id, String comment, long bookId);

    void deleteById(long id);
}
