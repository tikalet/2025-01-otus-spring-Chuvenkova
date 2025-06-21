package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.config.AppRunner;
import ru.otus.hw.models.AnalysisTest;
import ru.otus.hw.models.Description;
import ru.otus.hw.models.Measurement;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DoctorGatewayTest {

    @Autowired
    private DoctorGateway doctorGateway;

    @MockitoBean
    private AppRunner appRunner;

    @MockitoBean
    private DoctorService doctorService;

    @Test
    void shouldSendDataFromLaboratoryToDoctor() {
        AnalysisTest analysisTest = new AnalysisTest();
        analysisTest.setPatient("Test Test Test");
        analysisTest.setMeasurementList(List.of(new Measurement("Measurement", true)));
        analysisTest.setAnalysisName("Test analyse");

        Description description = new Description();
        description.setDoctor("Doctor Doctor Doctor");
        description.setPatient("Test Test Test");
        description.setMeasurementList(List.of(new Measurement("Measurement", true)));
        description.setAnalysisName("Test analyse");

        when(doctorService.signTest(analysisTest)).thenReturn(description);

        var expectedDescription = doctorGateway.process(analysisTest);

        assertThat(expectedDescription).isEqualTo(description);
    }
}
