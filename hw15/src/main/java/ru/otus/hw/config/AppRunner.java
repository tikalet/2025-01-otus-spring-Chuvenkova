package ru.otus.hw.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.otus.hw.service.ProcedureRoomService;

@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final ProcedureRoomService procedureRoomService;

    @Override
    public void run(String... args) throws Exception {
        procedureRoomService.startReceivingPatients();
    }
}
