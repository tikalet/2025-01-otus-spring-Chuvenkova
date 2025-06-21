package ru.otus.hw.models;

import lombok.Data;

import java.util.List;

@Data
public class Description {

    private String patient;

    private String doctor;

    private String analysisName;

    private List<Measurement> measurementList;
}
