package ru.otus.hw.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.hw.models.AnalysisTest;
import ru.otus.hw.models.Description;

@Slf4j
@Service
public class DoctorServiceImpl implements DoctorService {

    @Override
    public Description signTest(AnalysisTest analysisTest) {
        Description description = new Description();
        description.setPatient(analysisTest.getPatient());
        description.setMeasurementList(analysisTest.getMeasurementList());
        description.setAnalysisName(analysisTest.getAnalysisName());
        description.setDoctor("Врач КЛД Колбова Диана Савельевна");

//        log.info("sign " + description);
        return description;
    }
}
