package ru.otus.hw.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.models.Book;

@RequiredArgsConstructor
@Component
public class BookMapper {

    private final AuthorMapper authorMapper;

    private final GenreMapper genreMapper;

    public static BookDto fromModel(Book book) {
        return new BookDto(book.getId(), book.getTitle(),
                AuthorMapper.fromModel(book.getAuthor()),
                GenreMapper.fromModel(book.getGenre()));
    }

    public static Book toModel(BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getTitle(),
                AuthorMapper.toModel(bookDto.getAuthor()),
                GenreMapper.toModel(bookDto.getGenre()));
    }
}
