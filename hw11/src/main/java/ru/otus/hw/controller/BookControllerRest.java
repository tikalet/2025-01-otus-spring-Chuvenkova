package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.mapper.BookMapper;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.repositories.GenreRepository;

@RequiredArgsConstructor
@RestController
public class BookControllerRest {

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    private final CommentRepository commentRepository;

    private final BookMapper bookMapper;

    @GetMapping("/api/book")
    public Flux<BookDto> getAllBook() {
        return bookRepository.findAll()
                .map(bookMapper::fromModel);
    }

    @GetMapping("/api/book/{id}")
    public Mono<ResponseEntity<BookDto>> getBookById(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(createBookError(id))
                .map(bookMapper::fromModel)
                .map(ResponseEntity::ok);
    }


    @PostMapping("/api/book")
    public Mono<ResponseEntity<BookDto>> createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        return authorRepository.findById(bookCreateDto.getAuthorId())
                .switchIfEmpty(createAuthorError(bookCreateDto.getAuthorId()))
                .flatMap(author ->
                        genreRepository.findById(bookCreateDto.getGenreId())
                                .switchIfEmpty(createGenreError(bookCreateDto.getGenreId()))
                                .flatMap(genre -> {
                                    Book newBook = new Book(bookCreateDto.getTitle(), author, genre);
                                    return bookRepository.save(newBook)
                                            .map(bookMapper::fromModel)
                                            .map(ResponseEntity::ok);
                                })
                );
    }


    @PutMapping("/api/book")
    public Mono<ResponseEntity<BookDto>> updateBook(@Valid @RequestBody BookUpdateDto bookUpdateDto) {
        return bookRepository.findById(bookUpdateDto.getId())
                .switchIfEmpty(createBookError(bookUpdateDto.getId()))
                .flatMap(book ->
                        authorRepository.findById(bookUpdateDto.getAuthorId())
                                .switchIfEmpty(createAuthorError(bookUpdateDto.getAuthorId()))
                                .flatMap(author ->
                                        genreRepository.findById(bookUpdateDto.getGenreId())
                                                .switchIfEmpty(createGenreError(bookUpdateDto.getGenreId()))
                                                .flatMap(genre -> {
                                                    book.setAuthor(author);
                                                    book.setGenre(genre);
                                                    book.setTitle(bookUpdateDto.getTitle());
                                                    return bookRepository.save(book)
                                                            .map(bookMapper::fromModel)
                                                            .map(ResponseEntity::ok);
                                                })
                                ));
    }


    @DeleteMapping("/api/book/{id}")
    public Mono<ResponseEntity<Void>> deleteBook(@PathVariable("id") String id) {
        return bookRepository.deleteById(id)
                .then(commentRepository.deleteByBookId(id))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    private static Mono<Book> createBookError(String id) {
        return Mono.error(new NotFoundException("Book with id %s not found".formatted(id)));
    }

    private static Mono<Author> createAuthorError(String id) {
        return Mono.error(new NotFoundException("Author with id %s not found".formatted(id)));
    }

    private static Mono<Genre> createGenreError(String id) {
        return Mono.error(new NotFoundException("Genre with id %s not found".formatted(id)));
    }
}
