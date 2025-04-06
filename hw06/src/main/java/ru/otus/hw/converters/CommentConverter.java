package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Comment;

@RequiredArgsConstructor
@Component
public class CommentConverter {

    private final BookConverter bookConverter;

    public String bookCommentToString(Comment comment) {
        return "Id: %d, comment: %s".formatted(
                comment.getId(),
                comment.getCommentText());
    }

}
