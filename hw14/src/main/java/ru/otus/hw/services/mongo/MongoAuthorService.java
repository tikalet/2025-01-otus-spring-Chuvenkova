package ru.otus.hw.services.mongo;

import ru.otus.hw.models.mongo.MongoAuthor;

import java.util.List;

public interface MongoAuthorService {
    List<MongoAuthor> findAll();

}
