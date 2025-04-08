package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с жанрами ")
@DataJpaTest
@Import({JpaGenreRepository.class})
public class JpaGenreRepositoryTest {

    private static final long FIRST_ID = 1;

    @Autowired
    private JpaGenreRepository repositoryJpa;

    @Autowired
    private TestEntityManager entityManager;

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4).boxed()
                .map(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    @DisplayName("должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresList() {
        var actualGenres = repositoryJpa.findAll();

        assertThat(actualGenres).isNotNull()
                .allMatch(genre -> genre.getName() != null && !genre.getName().isEmpty());
        actualGenres.forEach(System.out::println);
    }

    @DisplayName("должен загружать жанр по id")
    @Test
    void shouldReturnCorrectGenreById() {
        var actualGenre = repositoryJpa.findById(FIRST_ID);
        var expectedGenre = entityManager.find(Genre.class, FIRST_ID);
        assertThat(actualGenre).isPresent()
                .get()
                .isEqualTo(expectedGenre);
    }

}
