package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;

    private String commentText;

    private BookDto book;

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

    @Override
    public String toString() {
        return "CommentDto{" +
                "id=" + id +
                ", commentText='" + commentText + '\'' +
                ", book=" + book +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentDto that = (CommentDto) o;
        return id == that.id && Objects.equals(commentText, that.commentText) && Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentText, book);
    }
}
