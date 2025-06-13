package ru.otus.hw.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.mongo.MongoAuthor;
import ru.otus.hw.models.relation.Author;
import ru.otus.hw.repositories.relation.AuthorRepository;

@RequiredArgsConstructor
@Service
public class AuthorMigrateService {

    private final AuthorRepository authorRepository;

    private final CacheManager cacheManager;

    @Cacheable(cacheNames = "authorCache", key = "#mongoAuthor.id")
    public Author save(MongoAuthor mongoAuthor) {
        Author author = new Author();
        author.setFullName(mongoAuthor.getFullName());

        return authorRepository.save(author);
    }

    public Author getAuthorByMongoId(String mongoAuthorId) {
        return cacheManager.getCache("authorCache").get(mongoAuthorId, Author.class);
    }
}
