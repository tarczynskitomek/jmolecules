package it.tarczynski.jmolecules.timeslot.infrastructure.config;

import it.tarczynski.jmolecules.timeslot.application.TimeSlotFacade;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeSlotFacadeConfig {

    @Bean
    TimeSlotFacade timeSlotFacade(TimeSlotRepository repository) {
        return new TimeSlotFacade(repository);
    }
}
