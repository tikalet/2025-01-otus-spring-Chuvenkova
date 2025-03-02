package ru.otus.hw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.tool.TestTextPrintTool;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private static final int DEFAULT_ANSWER_NUMBER = -1;

    private static final int MIN_ANSWER_NUM = 1;

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Autowired
    public TestServiceImpl(LocalizedIOService ioService, QuestionDao questionDao) {
        this.ioService = ioService;
        this.questionDao = questionDao;
    }

    @Override
    public TestResult executeTestFor(Student student) {
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        startTest(questions, testResult);

        return testResult;
    }

    private void startTest(List<Question> questions, TestResult testResult) {
        for (var question : questions) {
            var answerNumber = getAnswerToQuestion(question);
            var isAnswerValid = checkEnteredAnswer(question, answerNumber);
            testResult.applyAnswer(question, isAnswerValid);
        }
    }

    private int getAnswerToQuestion(Question question) {
        ioService.printLine(TestTextPrintTool.generateTextForPrint(question));

        var answerNumber = DEFAULT_ANSWER_NUMBER;

        try {
            answerNumber = ioService.readIntForRangeLocalized(MIN_ANSWER_NUM, question.answers().size(),
                    "TestService.enter.answer.number");
        } catch (IllegalArgumentException e) {
            ioService.printLine("");
            ioService.printLineLocalized("TestService.repeat.question");
            return getAnswerToQuestion(question);
        }

        return answerNumber;
    }

    private boolean checkEnteredAnswer(Question question, int personAnswerNumber) {
        return question.answers().get(personAnswerNumber - 1).isCorrect();
    }

}
