package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class CsvQuestionDaoTest {

    private TestFileNameProvider fileNameProvider;

    private CsvQuestionDao questionDao;

    @BeforeEach
    void setUp() {
        fileNameProvider = mock(TestFileNameProvider.class);
        questionDao = new CsvQuestionDao(fileNameProvider);
    }

    @DisplayName("Should generate the expected exception when the file is not found")
    @Test
    void shouldGenerateExpectedExceptionWhenFileDoesNotFound() {
        given(fileNameProvider.getTestFileName())
                .willReturn("error_name.csv")
                .willReturn("test_questions.csv");

        assertThatCode(() -> questionDao.findAll()).isInstanceOf(QuestionReadException.class);
        assertThatCode(() -> questionDao.findAll()).doesNotThrowAnyException();
    }
}
