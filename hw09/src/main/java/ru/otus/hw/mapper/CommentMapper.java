package ru.otus.hw.mapper;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

@Component
public class CommentMapper {

    public static CommentDto fromModel(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentText(comment.getCommentText());
        commentDto.setId(comment.getId());
        commentDto.setBook(new BookDto());
        commentDto.getBook().setId(comment.getBook().getId());
        return commentDto;
    }

    public static Comment toModel(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setCommentText(commentDto.getCommentText());
        comment.setId(commentDto.getId());
        comment.setBook(new Book());
        comment.getBook().setId(commentDto.getBook().getId());
        return comment;
    }

}
