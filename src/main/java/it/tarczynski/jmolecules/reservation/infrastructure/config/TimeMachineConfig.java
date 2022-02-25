package it.tarczynski.jmolecules.reservation.infrastructure.config;

import it.tarczynski.jmolecules.shared.TimeMachine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeMachineConfig {

    @Bean
    TimeMachine timeMachine() {
        return new TimeMachine() {
        };
    }
}
