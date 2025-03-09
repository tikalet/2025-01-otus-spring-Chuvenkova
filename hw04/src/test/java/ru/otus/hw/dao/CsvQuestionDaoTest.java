package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.exceptions.QuestionReadException;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = CsvQuestionDao.class)
public class CsvQuestionDaoTest {

    @MockitoBean
    private TestFileNameProvider fileNameProvider;

    @Autowired
    private CsvQuestionDao questionDao;

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
