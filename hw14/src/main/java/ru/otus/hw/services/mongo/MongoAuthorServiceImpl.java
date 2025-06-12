package ru.otus.hw.services.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.mongo.MongoAuthor;
import ru.otus.hw.repositories.mongo.MongoAuthorRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MongoAuthorServiceImpl implements MongoAuthorService {
    private final MongoAuthorRepository mongoAuthorRepository;

    @Override
    public List<MongoAuthor> findAll() {
        return mongoAuthorRepository.findAll();
    }

}
