package ru.otus.hw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.PollerSpec;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.hw.service.DoctorService;
import ru.otus.hw.service.LaboratoryService;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> procedureRoomChannel() {
        return MessageChannels.queue(5);
    }

    @Bean
    public MessageChannelSpec<?, ?> laboratoryChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public MessageChannelSpec<?, ?> doctorChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerSpec poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2);
    }

    @Bean
    public IntegrationFlow laboratoryFlow(LaboratoryService laboratoryService) {
        return IntegrationFlow.from(procedureRoomChannel())
                .split()
                .handle(laboratoryService, "doOrder")
                .aggregate()
                .channel(laboratoryChannel())
                .get();
    }

    @Bean
    public IntegrationFlow doctorFlow(DoctorService doctorService) {
        return IntegrationFlow.from(laboratoryChannel())
                .split()
                .handle(doctorService, "signTest")
                .aggregate()
                .channel(doctorChannel())
                .get();
    }

}
