package ru.otus.hw;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.service.TestRunnerServiceImpl;

@SpringBootTest
class HwApplicationTests {

    @MockitoBean
    private TestRunnerServiceImpl testRunnerService;

    @Test
    void contextLoads() {
    }

}
