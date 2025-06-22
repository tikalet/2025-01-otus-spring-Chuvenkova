package ru.otus.hw.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.config.AppRunner;
import ru.otus.hw.models.AnalysisTest;
import ru.otus.hw.models.Description;
import ru.otus.hw.models.LaboratoryOrder;
import ru.otus.hw.models.Measurement;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LaboratoryGatewayTest {

    @MockitoBean
    private AppRunner appRunner;

    @MockitoBean
    private DoctorService doctorService;

    @MockitoBean
    private LaboratoryService laboratoryService;

    @Autowired
    private LaboratoryGateway laboratoryGateway;

    @Test
    void shouldSendLaboratoryOrderAndReceiveDescription() {
        LaboratoryOrder laboratoryOrder = new LaboratoryOrder("Test Test Test", "Test analysis");

        AnalysisTest analysisTest = new AnalysisTest();
        analysisTest.setPatient("Test Test Test");
        analysisTest.setMeasurementList(List.of(new Measurement("Measurement", true)));
        analysisTest.setAnalysisName("Test analysis");

        Description description = new Description();
        description.setDoctor("Doctor Doctor Doctor");
        description.setPatient("Test Test Test");
        description.setMeasurementList(List.of(new Measurement("Measurement", true)));
        description.setAnalysisName("Test analysis");

        when(laboratoryService.doOrder(laboratoryOrder)).thenReturn(analysisTest);
        when(doctorService.signTest(analysisTest)).thenReturn(description);

        Collection<Description> expectedDescriptionList = laboratoryGateway.process(List.of(laboratoryOrder));
        assertThat(expectedDescriptionList).isNotNull().isNotEmpty();

        var expectedDescription = expectedDescriptionList.stream().findFirst();
        assertThat(expectedDescription.get()).isEqualTo(description);
    }

}
