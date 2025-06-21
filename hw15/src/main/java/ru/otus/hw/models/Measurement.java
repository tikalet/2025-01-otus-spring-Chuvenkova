package ru.otus.hw.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Measurement {

    private String name;

    private boolean normal;


    public Measurement(String name, boolean normal) {
        this.name = name;
        this.normal = normal;
    }
}
