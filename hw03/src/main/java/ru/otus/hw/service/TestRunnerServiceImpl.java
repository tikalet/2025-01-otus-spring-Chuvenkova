package ru.otus.hw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.hw.exceptions.QuestionReadException;

@Component
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final LocalizedIOService ioService;

    @Autowired
    public TestRunnerServiceImpl(TestService testService, StudentService studentService,
                                 ResultService resultService, LocalizedIOService ioService) {
        this.testService = testService;
        this.studentService = studentService;
        this.resultService = resultService;
        this.ioService = ioService;
    }

    @Override
    public void run() {
        var student = studentService.determineCurrentStudent();

        try {
            var testResult = testService.executeTestFor(student);
            resultService.showResult(testResult);
        } catch (QuestionReadException e) {
            ioService.printLineLocalized("TestRunnerService.unable.read.question");
        }
    }
}
