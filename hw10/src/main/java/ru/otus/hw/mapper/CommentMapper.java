package ru.otus.hw.mapper;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentCreateDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

@Component
public class CommentMapper {

    public CommentDto fromModel(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentText(comment.getCommentText());
        commentDto.setId(comment.getId());
        commentDto.setBook(new BookDto());
        commentDto.getBook().setId(comment.getBook().getId());
        return commentDto;
    }

    public Comment toModel(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setCommentText(commentDto.getCommentText());
        comment.setId(commentDto.getId());
        comment.setBook(new Book());
        comment.getBook().setId(commentDto.getBook().getId());
        return comment;
    }

    public CommentCreateDto toSaveDto(CommentDto commentDto) {
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        commentCreateDto.setCommentText(commentDto.getCommentText());
        commentCreateDto.setId(commentDto.getId());
        commentCreateDto.setBookId(commentDto.getBook().getId());
        return commentCreateDto;
    }
}
