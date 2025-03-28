package ru.otus.hw.converters.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookCommentDto;
import ru.otus.hw.models.BookComment;

@RequiredArgsConstructor
@Component
public class BookCommentDtoConverter {

    private final BookDtoConverter bookDtoConverter;

    public BookCommentDto toDto(BookComment bookComment) {
        var dto = new BookCommentDto();
        dto.setId(bookComment.getId());
        dto.setCommentText(bookComment.getCommentText());
        dto.setBook(bookDtoConverter.toDto(bookComment.getBook()));
        return dto;
    }

}
