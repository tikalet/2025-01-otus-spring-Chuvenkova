package ru.otus.hw.services;

import ru.otus.hw.models.BookComment;

import java.util.List;
import java.util.Optional;

public interface BookCommentService {

    Optional<BookComment> findById(long id);

    List<BookComment> findByBookId(long bookId);

    BookComment insert(String comment, long bookId);

    BookComment update(long id, String comment, long bookId);

    void deleteById(long id);
}
