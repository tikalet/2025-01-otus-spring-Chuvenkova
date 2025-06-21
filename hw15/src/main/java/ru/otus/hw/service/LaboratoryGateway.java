package ru.otus.hw.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.hw.models.LaboratoryOrder;

import java.util.Collection;

@MessagingGateway
public interface LaboratoryGateway {

    @Gateway(requestChannel = "procedureRoomChannel", replyChannel = "laboratoryChannel")
    void process(Collection<LaboratoryOrder> laboratoryOrderCollection);
}
