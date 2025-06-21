package ru.otus.hw.service;

import ru.otus.hw.models.AnalysisTest;
import ru.otus.hw.models.LaboratoryOrder;

public interface LaboratoryService {

    AnalysisTest doOrder(LaboratoryOrder laboratoryOrder);
}
