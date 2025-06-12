package ru.otus.hw.services.mongo;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.models.mongo.MongoComment;
import ru.otus.hw.repositories.mongo.MongoBookRepository;
import ru.otus.hw.repositories.mongo.MongoCommentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MongoCommentServiceImpl implements MongoCommentService {

    private final MongoCommentRepository mongoCommentRepository;

    private final MongoBookRepository mongoBookRepository;

    @Override
    public List<MongoComment> findByBookId(String bookId) {
        getBook(bookId);
        return mongoCommentRepository.findByBook(bookId);
    }

    private MongoBook getBook(String bookId) {
        return mongoBookRepository.findById(bookId).
                orElseThrow(() -> new EntityNotFoundException("Book with id %s not found".formatted(bookId)));
    }
}
