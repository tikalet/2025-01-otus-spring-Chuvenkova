package ru.otus.hw.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.models.AnalysisTest;
import ru.otus.hw.models.Description;

@MessagingGateway
public interface DoctorGateway {

    @Gateway(requestChannel = "laboratoryChannel", replyChannel = "doctorChannel")
    Description process(AnalysisTest analysisTests);
}
