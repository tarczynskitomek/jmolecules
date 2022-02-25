package it.tarczynski.jmolecules.reservable.infrastructure.config;

import it.tarczynski.jmolecules.reservable.application.ReservationEventHandler;
import it.tarczynski.jmolecules.reservable.domain.ReservableResourceRepository;
import it.tarczynski.jmolecules.reservable.infrastructure.event.ReservationEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReservableReservationEventListenerConfig {

    @Bean("reservableReservationEventListener")
    ReservationEventListener reservationEventListener(ReservableResourceRepository resourceRepository) {
        final var eventHandler = new ReservationEventHandler(resourceRepository);
        return new ReservationEventListener(eventHandler);
    }

}
