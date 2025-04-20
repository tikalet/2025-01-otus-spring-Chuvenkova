package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.hw.models.Book;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private long id;

    private String title;

    private AuthorDto author;

    private GenreDto genre;

    public static BookDto fromModel(Book book) {
        return new BookDto(book.getId(), book.getTitle(),
                AuthorDto.fromModel(book.getAuthor()),
                GenreDto.fromModel(book.getGenre()));
    }

    public static Book toModel(BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getTitle(),
                AuthorDto.toModel(bookDto.getAuthor()),
                GenreDto.toModel(bookDto.getGenre()));
    }
}
