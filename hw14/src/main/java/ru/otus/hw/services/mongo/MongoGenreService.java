package ru.otus.hw.services.mongo;

import ru.otus.hw.models.mongo.MongoGenre;

import java.util.List;

public interface MongoGenreService {
    List<MongoGenre> findAll();
}
