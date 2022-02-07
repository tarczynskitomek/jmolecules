package it.tarczynski.jmolecules.reservation.infrastructure.config;

import it.tarczynski.jmolecules.reservation.application.ReservationFacade;
import it.tarczynski.jmolecules.reservation.domain.ReservationRepository;
import it.tarczynski.jmolecules.reservation.infrastructure.repository.PostgresReservationRepository;
import it.tarczynski.jmolecules.shared.EventBus;
import it.tarczynski.jmolecules.shared.TimeMachine;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReservationFacadeConfiguration {

    @Bean
    ReservationFacade reservationFacade(
            TimeMachine timeMachine,
            ReservationRepository reservationRepository,
            EventBus eventBus
    ) {
        return new ReservationFacade(timeMachine, reservationRepository, eventBus);
    }

    @Bean
    ReservationRepository reservationRepository(DSLContext context) {
        return new PostgresReservationRepository(context);
    }
}
