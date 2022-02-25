package it.tarczynski.jmolecules.reservation.infrastructure.config;

import it.tarczynski.jmolecules.shared.infrastructure.event.SpringEventBus;
import it.tarczynski.jmolecules.shared.EventBus;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventBusConfig {

    @Bean
    EventBus eventBus(ApplicationEventPublisher eventPublisher) {
        return new SpringEventBus(eventPublisher);
    }
}
