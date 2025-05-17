package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AuthorControllerRest {
    private final AuthorService authorService;

    @GetMapping("/api/author")
    public ResponseEntity<List<AuthorDto>> getAllAuthor() {
        return new ResponseEntity<>(authorService.findAll(), HttpStatus.OK);
    }
}
