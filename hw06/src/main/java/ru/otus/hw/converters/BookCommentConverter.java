package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookCommentDto;
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

    public String bookCommentToString(BookCommentDto bookCommentDto) {
        return ("Id: %d, comment: %s, Book: {%s}").formatted(
                bookCommentDto.getId(),
                bookCommentDto.getCommentText(),
                bookConverter.bookToString(bookCommentDto.getBook()));
    }

}
