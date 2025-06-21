package ru.otus.hw.service;

import ru.otus.hw.models.AnalysisTest;
import ru.otus.hw.models.Description;

public interface DoctorService {

    Description signTest(AnalysisTest analysisTest);
}
