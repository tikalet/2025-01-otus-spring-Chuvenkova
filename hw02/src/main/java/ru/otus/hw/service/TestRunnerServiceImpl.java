package ru.otus.hw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.hw.exceptions.QuestionReadException;

@Service
public class TestRunnerServiceImpl implements TestRunnerService {

    private final TestService testService;

    private final StudentService studentService;

    private final ResultService resultService;

    private final IOService ioService;

    @Autowired
    public TestRunnerServiceImpl(TestService testService, StudentService studentService,
                                 ResultService resultService, IOService ioService) {
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
            ioService.printLine("Sorry! Unable to read question");
        }
    }
}
