package ru.otus.hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.services.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookControllerRest {

    private final BookService bookService;

    @GetMapping("/api/book")
    public ResponseEntity<List<BookDto>> getAllBook() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/api/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PostMapping("/api/book")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        var bookDto = bookService.create(bookCreateDto);
        return new ResponseEntity<BookDto>(bookDto, HttpStatus.CREATED);
//        return ResponseEntity.created(
//                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(bookDto.getId()).toUri()
//        ).build();
    }

    @PutMapping("/api/book")
    public ResponseEntity<BookDto> updateBook(@Valid @RequestBody BookUpdateDto bookUpdateDto) {
        var bookDto = bookService.update(bookUpdateDto);
        return ResponseEntity.ok(bookDto);
    }

    @DeleteMapping("/api/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
