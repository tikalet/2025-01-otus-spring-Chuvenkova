package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CommentController {
    private final CommentService commentService;

    @GetMapping(value = "/comments", params = "bookId")
    public String commentForBookPage(@RequestParam("bookId") long bookId, Model model) {
        List<CommentDto> comments = commentService.findByBookId(bookId);
        model.addAttribute("comments", comments);
        return "comments";
    }
}
