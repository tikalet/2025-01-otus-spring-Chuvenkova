package ru.otus.hw.converters.mongo;

import org.springframework.stereotype.Component;
import ru.otus.hw.models.mongo.MongoAuthor;

@Component
public class MongoAuthorConverter {
    public String authorToString(MongoAuthor author) {
        return "Id: %s, FullName: %s".formatted(author.getId(), author.getFullName());
    }

}
