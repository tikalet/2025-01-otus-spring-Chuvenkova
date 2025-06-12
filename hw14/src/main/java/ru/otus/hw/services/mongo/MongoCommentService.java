package ru.otus.hw.services.mongo;

import ru.otus.hw.models.mongo.MongoComment;

import java.util.List;

public interface MongoCommentService {

    List<MongoComment> findByBookId(String bookId);

}
