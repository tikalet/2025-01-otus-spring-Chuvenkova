package ru.otus.hw.tool;

import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

public class TestTextPrintTool {

    public static String generateTextForPrint(Question question) {
        StringBuilder resultText = new StringBuilder();
        int answerIndex = 1;

        resultText.append("\n").append(question.text()).append("\n");

        for (Answer answer : question.answers()) {
            resultText.append(answerIndex).append(" ").append(answer.text()).append("\n");
            answerIndex++;
        }

        return resultText.toString();
    }
}
