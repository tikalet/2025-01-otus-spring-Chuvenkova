package ru.otus.hw.models;

import lombok.Data;

@Data
public class LaboratoryOrder {

    private String patient;

    private String analysisName;

    public LaboratoryOrder(String patient, String analysisName) {
        this.patient = patient;
        this.analysisName = analysisName;
    }
}
