package ru.otus.hw.converters.dto;

import org.springframework.stereotype.Component;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.models.Author;

@Component
public class AuthorDtoConverter {

    public AuthorDto toDto(Author author) {
        var dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setFullName(author.getFullName());
        return dto;
    }
}
