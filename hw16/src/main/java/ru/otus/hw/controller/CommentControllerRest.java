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
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentUpdateDto;
import ru.otus.hw.services.CommentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentControllerRest {

    private final CommentService commentService;

    @GetMapping(value = "/api/comment/book/{id}")
    public ResponseEntity<List<CommentDto>> commentByBookId(@PathVariable("id") long id) {
        List<CommentDto> comments = commentService.findByBookId(id);
        return ResponseEntity.ok(comments);
    }

    @GetMapping(value = "/api/comment/{id}")
    public ResponseEntity<CommentDto> commentById(@PathVariable("id") long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @PostMapping("/api/comment")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentCreateDto commentCreateDto) {
        var commentDto = commentService.create(commentCreateDto);
        return ResponseEntity.ok(commentDto);
    }

    @PutMapping("/api/comment")
    public ResponseEntity<CommentDto> updateComment(@Valid @RequestBody CommentUpdateDto commentUpdateDto) {
        var commentDto = commentService.update(commentUpdateDto);
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("/api/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable("id") long id) {
        commentService.deleteById(id);
        return ResponseEntity.ok("OK");
    }

}
