package ru.otus.hw.cache;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PatientCache {

    private static final String[] PATIENT = {
            "Иванов Аркадий Петрович",
            "Пуговкина Лея Валентиновна",
            "Сорокина Алла Николаевна",
            "Дурко Михаил Олегович",
            "Жуков Илья Ильич",
            "Хренов Акакий Акакиевич",
            "Пирогов Борис Леонидович",
            "Жаркова Ирина Ивановна",
            "Баранов Александр Давидович",
            "Гоголь Савелий Николаевич"
    };

    public String get() {
        return PATIENT[new Random().nextInt(0, PATIENT.length)];
    }
}
