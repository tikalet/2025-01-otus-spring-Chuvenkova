package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.cache.AnalysisCache;
import ru.otus.hw.cache.PatientCache;
import ru.otus.hw.models.Description;
import ru.otus.hw.models.LaboratoryOrder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProcedureRoomServiceImpl implements ProcedureRoomService {

    private final LaboratoryGateway laboratoryGateway;

    private final PatientCache patientCache;

    private final AnalysisCache analysisCache;

    @Override
    public void startReceivingPatients() {
        for (int peopeleCount = 0; peopeleCount < 5; peopeleCount++) {
            int orderCountForPeople = new Random().nextInt(1, 3);

            var laboratoryOrderList = new ArrayList<LaboratoryOrder>();
            var patient = patientCache.get();

            for (int orderCount = 0; orderCount < orderCountForPeople; orderCount++) {
                LaboratoryOrder laboratoryOrder = new LaboratoryOrder(patient, analysisCache.getAnalysis());
                laboratoryOrderList.add(laboratoryOrder);
                log.info("make " + laboratoryOrder);
            }

            delay();

            Collection<Description> descriptions = laboratoryGateway.process(laboratoryOrderList);
            descriptions.forEach(this::printDescription);
        }
    }

    private void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void printDescription(Description description) {
        var text = "\nЗаключение\nПациент %s \nИсследование \"%s\"\n%s\n%s".formatted(description.getPatient(),
                description.getAnalysisName(),
                description.getMeasurementList().stream().map(m -> m.getName() + " " + m.isNormal())
                        .collect(Collectors.joining(" || ")),
                description.getDoctor());

        log.info(text);
    }
}
