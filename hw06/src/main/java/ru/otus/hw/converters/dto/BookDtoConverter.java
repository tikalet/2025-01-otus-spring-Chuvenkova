package ru.otus.hw.converters.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;

@RequiredArgsConstructor
@Component
public class BookDtoConverter {

    private final GenreDtoConverter genreDtoConverter;

    private final AuthorDtoConverter authorDtoConverter;

    public BookDto toDto(Book book) {
        var dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(authorDtoConverter.toDto(book.getAuthor()));
        dto.setGenre(genreDtoConverter.toDto(book.getGenre()));
        return dto;
    }
}
