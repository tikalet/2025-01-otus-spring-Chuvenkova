package ru.otus.hw.services;

import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> findById(String id);

    List<Comment> findByBookId(String bookId);

    Comment insert(String bookId, String comment);

    Comment update(String id, String comment);

    void deleteById(String id);
}
