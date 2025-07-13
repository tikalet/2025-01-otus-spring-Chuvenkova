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
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.mapper.CommentMapper;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

@RequiredArgsConstructor
@RestController
public class CommentControllerRest {

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    private final BookRepository bookRepository;

    @GetMapping(value = "/api/comment/book/{id}")
    public Flux<CommentDto> commentByBookId(@PathVariable("id") String id) {
        return bookRepository.findById(id)
                .switchIfEmpty(createBookError(id))
                .flatMapMany(book -> commentRepository.findByBookId(id))
                .map(commentMapper::fromModel);
    }

    @GetMapping(value = "/api/comment/{id}")
    public Mono<ResponseEntity<CommentDto>> commentById(@PathVariable("id") String id) {
        return commentRepository.findById(id)
                .map(commentMapper::fromModel)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/api/comment")
    public Mono<ResponseEntity<CommentDto>> createComment(@Valid @RequestBody CommentCreateDto commentCreateDto) {
        return bookRepository.findById(commentCreateDto.getBookId())
                .switchIfEmpty(createBookError(commentCreateDto.getBookId()))
                .flatMap(book -> {
                    Comment comment = new Comment(null, commentCreateDto.getCommentText(), book);
                    return commentRepository.save(comment)
                            .map(commentMapper::fromModel)
                            .map(ResponseEntity::ok);
                });
    }

    @PutMapping("/api/comment")
    public Mono<ResponseEntity<CommentDto>> updateComment(@Valid @RequestBody CommentUpdateDto commentUpdateDto) {
        return commentRepository.findById(commentUpdateDto.getId())
                .switchIfEmpty(createCommentError(commentUpdateDto.getId()))
                .flatMap(comment -> {
                    comment.setCommentText(commentUpdateDto.getCommentText());
                    return commentRepository.save(comment)
                            .map(commentMapper::fromModel)
                            .map(ResponseEntity::ok);
                });
    }

    @DeleteMapping("/api/comment/{id}")
    public Mono<ResponseEntity<Void>> deleteComment(@PathVariable("id") String id) {
        return commentRepository.deleteById(id)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    private static Mono<Book> createBookError(String id) {
        return Mono.error(new NotFoundException("Book with id %s not found".formatted(id)));
    }

    private static Mono<Comment> createCommentError(String id) {
        return Mono.error(new NotFoundException("Comment with id %s not found".formatted(id)));
    }
}
