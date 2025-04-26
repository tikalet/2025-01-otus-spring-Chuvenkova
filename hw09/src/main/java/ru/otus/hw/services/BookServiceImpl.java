package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.mapper.BookMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    public BookDto findById(long id) {
        return BookMapper.fromModel(getBook(id));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(BookMapper::fromModel).toList();
    }

    @Override
    @Transactional
    public BookDto create(String title, long authorId, long genreId) {
        var author = getAuthor(authorId);
        var genre = getGenre(genreId);
        var book = new Book(0, title, author, genre);
        book = bookRepository.save(book);
        return BookMapper.fromModel(book);
    }

    @Override
    @Transactional
    public BookDto update(long id, String title, long authorId, long genreId) {
        var book = getBook(id);
        var author = getAuthor(authorId);
        var genre = getGenre(genreId);

        book.setAuthor(author);
        book.setGenre(genre);
        book.setTitle(title);
        return BookMapper.fromModel(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bookRepository.deleteById(id);
    }

    private Genre getGenre(long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new NotFoundException("Genre with id %d not found".formatted(genreId)));
    }

    private Author getAuthor(long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException("Author with id %d not found".formatted(authorId)));
    }

    private Book getBook(long id) {
        return bookRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Book with id %d not found".formatted(id)));
    }
}
