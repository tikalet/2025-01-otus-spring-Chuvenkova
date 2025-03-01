package ru.otus.hw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.exceptions.AnswerConvertException;
import ru.otus.hw.exceptions.AnswerNumberSizeException;
import ru.otus.hw.tool.TestTextPrintTool;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private static final int DEFAULT_ANSWER_NUMBER = -1;

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
            if (!checkCorrectQuestion(question)) {
                continue;
            }

            var answerNumber = getAnswerToQuestion(question);
            var isAnswerValid = checkEnteredAnswer(question, answerNumber);
            testResult.applyAnswer(question, isAnswerValid);
        }
    }

    private int getAnswerToQuestion(Question question) {
        var answerNumberText = ioService.readStringWithPrompt(TestTextPrintTool.generateTextForPrint(question));

        int answerNumber = DEFAULT_ANSWER_NUMBER;

        try {
            answerNumber = convertStringToInt(answerNumberText);
            validAnswerNumber(question, answerNumber);
        } catch (AnswerConvertException | AnswerNumberSizeException e) {
            ioService.printLine("");
            ioService.printLine(e.getMessage());
            repeatQuestion(question);
        }

        return answerNumber;
    }

    private void repeatQuestion(Question question) {
        ioService.printFormattedLine("Try to answer the question again");
        getAnswerToQuestion(question);
    }

    private boolean checkEnteredAnswer(Question question, int personAnswerNumber) {
        int answerIndex = 1;

        for (Answer answer : question.answers()) {
            if (answerIndex == personAnswerNumber) {
                return answer.isCorrect();
            }
            answerIndex++;
        }

        return false;
    }

    private int convertStringToInt(String answerText) {
        try {
            return Integer.parseInt(answerText);
        } catch (NumberFormatException ex) {
            throw new AnswerConvertException("Please enter the answer number");
        }
    }

    private void validAnswerNumber(Question question, int answerNumber) {
        if (question.answers().size() >= answerNumber) {
            return;
        }

        throw new AnswerNumberSizeException("There is no such answer number");
    }

    private boolean checkCorrectQuestion(Question question) {
        if (question == null || question.answers() == null || question.answers().isEmpty()) {
            return false;
        }

        return true;
    }

}
