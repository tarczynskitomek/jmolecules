package it.tarczynski.jmolecules.reservation.infrastructure.config;

import it.tarczynski.jmolecules.reservation.application.ReservationChangeLog;
import it.tarczynski.jmolecules.reservation.infrastructure.event.ReservationEventListener;
import it.tarczynski.jmolecules.shared.ChangeLogRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReservationReservationEventListenerConfig {

    @Bean("reservationReservationEventListener")
    ReservationEventListener reservationEventListener(ChangeLogRepository logRepository) {
        final var changeLog = new ReservationChangeLog(logRepository);
        return new ReservationEventListener(changeLog);
    }
}
