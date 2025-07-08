package ru.otus.hw.controller;

import io.github.resilience4j.core.functions.CheckedFunction;
import io.github.resilience4j.retry.Retry;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import ru.otus.hw.config.StorageBookProperties;
import ru.otus.hw.dto.BookCreateDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.BookUpdateDto;
import ru.otus.hw.dto.StorageInfoDto;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class BookControllerRest {

    // curl http://localhost:8080/api/book/1

    private final BookService bookService;

    private final RestClient restClient;

    private final CheckedFunction<Long, StorageInfoDto> getFindStorageInfoFunction;

    public BookControllerRest(BookService bookService,
                              StorageBookProperties storageBookProperties,
                              CircuitBreaker circuitBreaker,
                              Retry retry) {
        this.bookService = bookService;

        this.restClient = RestClient.builder()
                .requestFactory(new HttpComponentsClientHttpRequestFactory())
                .baseUrl(storageBookProperties.getUrl())
                .build();

        this.getFindStorageInfoFunction = bookId -> circuitBreaker.run(
                () -> {
                    try {
                        return Retry.decorateCheckedFunction(retry, this::findStorageInfo)
                                .apply(bookId);
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                },
                t -> {
                    log.error("Circuit breaker fallback, error: {}", t.getMessage());
                    return null;
                }
        );
    }

    public StorageInfoDto findStorageInfo(Long bookId) {
        log.warn("try send request");
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/storage/api/book/{id}")
                        .build(Map.of("id", bookId)))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(StorageInfoDto.class);
    }

    @GetMapping("/api/book")
    public ResponseEntity<List<BookDto>> getAllBook() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/api/book/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Long id) {
        var bookDto = ResponseEntity.ok(bookService.findById(id));

        StorageInfoDto storageInfoDto = null;

        try {
            storageInfoDto = getFindStorageInfoFunction.apply(id);
//            storageInfoDto = findStorageInfo(id);

            if (storageInfoDto != null) {
                log.info("receive {}", storageInfoDto.toString());
            }
        } catch (Throwable e) {
            log.error("Unable send request. {}", e.getMessage());
        }

        return bookDto;
    }

    @PostMapping("/api/book")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        var bookDto = bookService.create(bookCreateDto);
        return new ResponseEntity<BookDto>(bookDto, HttpStatus.CREATED);
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
