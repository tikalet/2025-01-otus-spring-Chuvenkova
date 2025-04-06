package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.BookComment;

@RequiredArgsConstructor
@Component
public class BookCommentConverter {

    private final BookConverter bookConverter;

    public String bookCommentToString(BookComment bookComment) {
        return "Id: %d, comment: %s".formatted(
                bookComment.getId(),
                bookComment.getCommentText());
    }

}
