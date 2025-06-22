package ru.otus.hw.models;

import lombok.Data;

import java.util.List;

@Data
public class AnalysisTest {

    private String patient;

    private String analysisName;

    private List<Measurement> measurementList;

}
