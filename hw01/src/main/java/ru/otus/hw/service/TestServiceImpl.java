package ru.otus.hw.service;

import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

public class TestServiceImpl implements TestService {

    private static final String TEXT_SPACE = " ";

    private static final String TEXT_CLOSE_BRACKET = ")";

    private static final String TEXT_EMPTY = "";

    private static final String TEXT_COLON = ":";

    private final IOService ioService;

    private final QuestionDao questionDao;

    public TestServiceImpl(IOService ioService, QuestionDao questionDao) {
        this.ioService = ioService;
        this.questionDao = questionDao;
    }

    @Override
    public void executeTest() {
        ioService.printLine(TEXT_EMPTY);
        ioService.printFormattedLine("Please answer the questions below:");

        List<Question> questionList = questionDao.findAll();

        printQuestion(questionList);
    }

    private void printQuestion(List<Question> questionList) {
        if (questionList == null) {
            ioService.printFormattedLine("Unfortunately, the list of questions is empty");
            return;
        }

        int questionIndex = 1;
        for (Question question : questionList) {
            ioService.printLine(TEXT_EMPTY);
            ioService.printLine("Question " + questionIndex + TEXT_COLON + TEXT_SPACE + question.text());

            printAnswer(question.answers());
            questionIndex++;
        }
    }

    private void printAnswer(List<Answer> answers) {
        if (answers == null) {
            ioService.printFormattedLine("Unfortunately, no answers have been prepared for this question");
            return;
        }

        int answerIndex = 1;

        for (Answer answer : answers) {
            ioService.printFormattedLine(answerIndex + TEXT_CLOSE_BRACKET + TEXT_SPACE + answer.text());
            answerIndex++;
        }
    }
}
