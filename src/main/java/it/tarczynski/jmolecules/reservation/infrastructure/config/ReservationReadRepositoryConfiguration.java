package it.tarczynski.jmolecules.reservation.infrastructure.config;

import it.tarczynski.jmolecules.reservation.domain.view.ReservationReadModelRepository;
import it.tarczynski.jmolecules.reservation.infrastructure.repository.view.PostgresReservationReadModelRepository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ReservationReadRepositoryConfiguration {

    @Bean
    ReservationReadModelRepository reservationReadRepository(DSLContext context) {
        return new PostgresReservationReadModelRepository(context);
    }
}
