package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateDto {

    @Null
    private Long id;

    @NotBlank(message = "The title should not be empty")
    @Size(min = 3, max = 254, message = "The title must contain from 3 to 254 characters")
    private String title;

    @NotNull
    private Long authorId;

    @NotNull
    private Long genreId;
}
