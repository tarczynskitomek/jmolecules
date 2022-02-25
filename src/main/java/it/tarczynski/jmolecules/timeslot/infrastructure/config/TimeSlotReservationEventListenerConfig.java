package it.tarczynski.jmolecules.timeslot.infrastructure.config;

import it.tarczynski.jmolecules.timeslot.application.ReservationEventHandler;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotRepository;
import it.tarczynski.jmolecules.timeslot.infrastructure.event.ReservationEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TimeSlotReservationEventListenerConfig {

    @Bean("timeSlotReservationEventListener")
    ReservationEventListener reservationEventListener(TimeSlotRepository timeSlotRepository) {
        return new ReservationEventListener(
                new ReservationEventHandler(timeSlotRepository)
        );
    }
}
