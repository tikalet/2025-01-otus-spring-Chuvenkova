package ru.otus.hw.models.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "comment")
public class MongoComment {

    @Id
    private String id;

    private String commentText;

    @DBRef(lazy = true)
    private MongoBook book;

    public MongoComment(String commentText, MongoBook book) {
        this.commentText = commentText;
        this.book = book;
    }
}
