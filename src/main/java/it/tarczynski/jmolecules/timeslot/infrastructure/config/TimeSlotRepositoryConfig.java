package it.tarczynski.jmolecules.timeslot.infrastructure.config;

import it.tarczynski.jmolecules.timeslot.domain.TimeSlotRepository;
import it.tarczynski.jmolecules.timeslot.infrastructure.repository.PostgresTimeSlotRepository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeSlotRepositoryConfig {

    @Bean
    TimeSlotRepository timeSlotRepository(DSLContext context) {
        return new PostgresTimeSlotRepository(context);
    }
}
