package ru.otus.hw.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.mongo.MongoBook;
import ru.otus.hw.models.relation.Book;
import ru.otus.hw.repositories.relation.BookRepository;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BookMigrateService {

    private final BookRepository bookRepository;

    private final CacheManager cacheManager;

    private final AuthorMigrateService authorMigrateService;

    private final GenreMigrateService genreMigrateService;

    @Cacheable(cacheNames = "bookCache", key = "#mongoBook.id")
    public Book save(MongoBook mongoBook) {
        Book book = new Book();
        book.setTitle(mongoBook.getTitle());
        book.setAuthor(authorMigrateService.getAuthorByMongoId(mongoBook.getAuthor().getId()));
        book.setGenre(genreMigrateService.getGenreByMongoId(mongoBook.getGenre().getId()));
        return bookRepository.save(book);
    }

    public Book getBookByMongoId(String mongoId) {
        return Objects.requireNonNull(cacheManager.getCache("bookCache")).get(mongoId, Book.class);
    }
}
