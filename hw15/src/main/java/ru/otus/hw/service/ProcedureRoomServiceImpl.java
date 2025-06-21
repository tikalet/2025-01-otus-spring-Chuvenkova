package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.cache.AnalysisCache;
import ru.otus.hw.cache.PatientCache;
import ru.otus.hw.models.LaboratoryOrder;

import java.util.ArrayList;
import java.util.Random;

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

            laboratoryGateway.process(laboratoryOrderList);

            delay();
        }
    }

    private void delay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
