package ru.otus.hw.services.mongo;

import ru.otus.hw.models.mongo.MongoBook;

import java.util.List;

public interface MongoBookService {

    List<MongoBook> findAll();

}
