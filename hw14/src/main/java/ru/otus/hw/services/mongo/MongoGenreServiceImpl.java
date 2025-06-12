package ru.otus.hw.services.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.mongo.MongoGenre;
import ru.otus.hw.repositories.mongo.MongoGenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MongoGenreServiceImpl implements MongoGenreService {
    private final MongoGenreRepository mongoGenreRepository;

    @Override
    public List<MongoGenre> findAll() {
        return mongoGenreRepository.findAll();
    }
}
