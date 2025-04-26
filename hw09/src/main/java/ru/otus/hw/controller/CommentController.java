package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CommentController {
    private final CommentService commentService;

    @GetMapping(value = "/comment/book/{id}")
    public String commentForBookPage(@PathVariable("id") long id, Model model) {
        List<CommentDto> comments = commentService.findByBookId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("bookId", id);
        return "comments";
    }

    @GetMapping(value = "/comment/{id}")
    public String commentEditPage(@PathVariable("id") long id, Model model) {
        CommentDto commentDto = commentService.findById(id);
        model.addAttribute("comment", commentDto);
        return "comment_edit";
    }

    @PostMapping(value = "/comment")
    public String saveComment(CommentDto comment) {
        CommentDto savedCommentDto = null;

        if (comment.getId() == 0) {
            savedCommentDto = commentService.create(comment.getBook().getId(), comment.getCommentText());
        } else {
            savedCommentDto = commentService.update(comment.getId(), comment.getCommentText());
        }

        return "redirect:/comment/book/" + savedCommentDto.getBook().getId();
    }

    @PostMapping(value = "/comment/{id}/delete")
    public String deleteComment(@PathVariable("id") long id, Model model) {
        CommentDto commentDto = commentService.findById(id);
        commentService.deleteById(id);
        return "redirect:/comment/book/" + commentDto.getBook().getId();
    }

    @GetMapping(value = "/comment/book/{id}/create")
    public String commentNewPage(@PathVariable("id") long id, Model model) {
        CommentDto commentDto = new CommentDto();
        commentDto.setBook(new BookDto());
        commentDto.getBook().setId(id);

        model.addAttribute("comment", commentDto);
        return "comment_edit";
    }

}
