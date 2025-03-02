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

    private static final int MAX_ANSWER_NUM = 4;

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Autowired
    public TestServiceImpl(IOService ioService, QuestionDao questionDao) {
        this.ioService = ioService;
        this.questionDao = questionDao;
    }

    @Override
    public TestResult executeTestFor(Student student) {
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below. Specify only the answer number");
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
        var answerNumber = DEFAULT_ANSWER_NUMBER;

        try {
            answerNumber = ioService.readIntForRangeWithPrompt(MIN_ANSWER_NUM, MAX_ANSWER_NUM,
                    TestTextPrintTool.generateTextForPrint(question),
                    "There is no such answer number");
        } catch (IllegalArgumentException e) {
            ioService.printLine("");
            ioService.printFormattedLine("Try to answer the question again");
            return getAnswerToQuestion(question);
        }

        return answerNumber;
    }

    private boolean checkEnteredAnswer(Question question, int personAnswerNumber) {
        if (question.answers().size() <= (personAnswerNumber - 1)) {
            return false;
        }

        return question.answers().get(personAnswerNumber - 1).isCorrect();
    }

}
