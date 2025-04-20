package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookDto;
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
        model.addAttribute("bookId", bookId);
        return "comments";
    }

    @GetMapping(value = "/comment", params = "id")
    public String commentEditPage(@RequestParam("id") long id, Model model) {
        CommentDto commentDto = commentService.findById(id);
        model.addAttribute("comment", commentDto);
        return "comment_edit";
    }

    @PostMapping(value = "/comment_edit")
    public String saveEditComment(CommentDto comment) {
        CommentDto updatedCommentDto = commentService.update(comment.getId(), comment.getCommentText());
        return "redirect:/comments?bookId=" + updatedCommentDto.getBook().getId();
    }

    @PostMapping(value = "/comment_delete", params = "id")
    public String deleteComment(@RequestParam("id") long id, Model model) {
        CommentDto commentDto = commentService.findById(id);
        commentService.deleteById(id);
        return "redirect:/comments?bookId=" + commentDto.getBook().getId();
    }

    @GetMapping(value = "/comment_new", params = "bookId")
    public String commentNewPage(@RequestParam("bookId") long bookId, Model model) {
        CommentDto commentDto = new CommentDto();
        commentDto.setBook(new BookDto());
        commentDto.getBook().setId(bookId);

        model.addAttribute("comment", commentDto);
        model.addAttribute("bookId", bookId);
        return "comment_new";
    }

    @PostMapping(value = "/comment_save")
    public String saveNewComment(CommentDto commentDto) {
        commentService.insert(commentDto.getBook().getId(), commentDto.getCommentText());
        return "redirect:/comments?bookId=" + commentDto.getBook().getId();
    }
}
