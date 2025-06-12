package ru.otus.hw.services.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.repositories.mongo.MongoBookRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MongoBookServiceImpl implements MongoBookService {

    private final MongoBookRepository mongoBookRepository;

    @Override
    public List<MongoBook> findAll() {
        return mongoBookRepository.findAll();
    }


}
