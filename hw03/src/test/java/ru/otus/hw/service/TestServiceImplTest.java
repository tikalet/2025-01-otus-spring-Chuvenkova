package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.tool.TestTextPrintTool;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestServiceImplTest {

    private static final int MIN_ANSWER_NUM = 1;

    private static final int MAX_QUESTION_COUNT = 3;

    private LocalizedIOService ioService;

    private CsvQuestionDao questionDao;

    private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
        ioService = mock(LocalizedIOService.class);
        questionDao = mock(CsvQuestionDao.class);

        testService = new TestServiceImpl(ioService, questionDao);
    }

    @DisplayName("Should take the test for all correct answers")
    @Test
    void shouldTakeTestForAllCorrectAnswers() {
        given(questionDao.findAll()).willReturn(createQuestionList());

        var question = createQuestionWithAnswers();
        var errorText = "TestService.enter.answer.number";
        given(ioService.readIntForRangeLocalized(MIN_ANSWER_NUM, question.answers().size(), errorText)).willReturn(1);

        testService = new TestServiceImpl(ioService, questionDao);
        TestResult testResult = testService.executeTestFor(createTestStudent());

        verify(questionDao, times(1)).findAll();
        verify(ioService, times(MAX_QUESTION_COUNT)).readIntForRangeLocalized(MIN_ANSWER_NUM, question.answers().size(), errorText);

        assertThat(testResult.getRightAnswersCount() == MAX_QUESTION_COUNT).isTrue();
    }

    private Student createTestStudent() {
        return new Student("Test", "Testov");
    }

    private List<Question> createQuestionList() {
        List<Question> result = new ArrayList<>();

        for (int i = 0; i < MAX_QUESTION_COUNT; i++) {
            result.add(createQuestionWithAnswers());
        }

        return result;
    }

    private Question createQuestionWithAnswers() {
        List<Answer> answerList = createAnswerList();

        return new Question("QuestionText", answerList);
    }

    private List<Answer> createAnswerList() {
        List<Answer> answerList = new ArrayList<>();
        Answer answerY = new Answer("yes", true);
        Answer answerN = new Answer("no", false);
        answerList.add(answerY);
        answerList.add(answerN);
        return answerList;
    }

}
