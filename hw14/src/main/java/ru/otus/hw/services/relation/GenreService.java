package ru.otus.hw.services.relation;

import ru.otus.hw.models.relation.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> findAll();
}
