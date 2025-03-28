package ru.otus.hw.services;

import ru.otus.hw.dto.BookCommentDto;
import ru.otus.hw.models.BookComment;

import java.util.List;

public interface BookCommentService {

    BookCommentDto findById(long id);

    List<BookComment> findByBookId(long bookId);

    BookComment insert(String comment, long bookId);

    BookComment update(long id, String comment, long bookId);

    void deleteById(long id);
}
