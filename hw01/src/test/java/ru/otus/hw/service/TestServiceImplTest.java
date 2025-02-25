package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.CsvQuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class TestServiceImplTest {

    private StreamsIOService ioService;

    private CsvQuestionDao questionDao;

    private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
        ioService = new StreamsIOService(System.out);
        questionDao = mock(CsvQuestionDao.class);

        testService = new TestServiceImpl(ioService, questionDao);
    }

    @DisplayName("Do test for start test")
    @Test
    void printAnswer() {
        given(questionDao.findAll())
//                .willReturn(null)
//                .willReturn(new ArrayList<Question>())
                .willReturn(createQuestionList());

        testService.executeTest();

        verify(questionDao, times(1)).findAll();
    }

    private List<Question> createQuestionList() {
        List<Question> result = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            result.add(createQuestionWithAnswers());
        }

        result.add(createQuestionWithoutAnswers());

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

    private Question createQuestionWithoutAnswers() {
        return new Question("QuestionText", null);
    }

}
