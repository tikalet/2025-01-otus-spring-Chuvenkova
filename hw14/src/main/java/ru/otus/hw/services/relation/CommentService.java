package ru.otus.hw.services.relation;

import ru.otus.hw.models.relation.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> findByBookId(long bookId);

}
