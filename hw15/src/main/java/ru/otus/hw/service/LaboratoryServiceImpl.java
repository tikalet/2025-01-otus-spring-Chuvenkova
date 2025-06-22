package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.cache.AnalysisCache;
import ru.otus.hw.models.AnalysisTest;
import ru.otus.hw.models.LaboratoryOrder;
import ru.otus.hw.models.Measurement;

import java.util.ArrayList;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class LaboratoryServiceImpl implements LaboratoryService {

    private final AnalysisCache analysisCache;

    @Override
    public AnalysisTest doOrder(LaboratoryOrder laboratoryOrder) {
        AnalysisTest analysisTest = new AnalysisTest();
        analysisTest.setPatient(laboratoryOrder.getPatient());
        analysisTest.setAnalysisName(laboratoryOrder.getAnalysisName());
        analysisTest.setMeasurementList(new ArrayList<>());

        for (String meas : analysisCache.getMeasurement(laboratoryOrder.getAnalysisName())) {
            Measurement measurement = new Measurement(meas, getNormalValue());
            analysisTest.getMeasurementList().add(measurement);
        }

//        log.info("do " + analysisTest);

        delay();

        return analysisTest;
    }

    private boolean getNormalValue() {
        return (new Random().nextInt(0, 100) % 2) == 0;
    }

    private static void delay() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
