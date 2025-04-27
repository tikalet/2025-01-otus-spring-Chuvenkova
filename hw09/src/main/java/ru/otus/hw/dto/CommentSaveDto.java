package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentSaveDto {

    private long id;

    @NotBlank(message = "The comment should not be empty.")
    private String commentText;

    private long bookId;
}
