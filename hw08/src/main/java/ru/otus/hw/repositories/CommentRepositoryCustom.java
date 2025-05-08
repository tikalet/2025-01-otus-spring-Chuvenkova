package ru.otus.hw.repositories;

import org.springframework.data.repository.query.Param;
import ru.otus.hw.models.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> findByBook(@Param("book") String book);

    void deleteByBook(@Param("book") String book);
}
