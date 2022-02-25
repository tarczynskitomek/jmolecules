package it.tarczynski.jmolecules.reservation.infrastructure.config;

import it.tarczynski.jmolecules.reservable.domain.ReservableResourceRepository;
import it.tarczynski.jmolecules.reservation.application.ReservationCommandFacade;
import it.tarczynski.jmolecules.reservation.domain.ReservationCreationPolicy;
import it.tarczynski.jmolecules.reservation.domain.ReservationRepository;
import it.tarczynski.jmolecules.reservation.domain.ReservationValidator;
import it.tarczynski.jmolecules.reservation.infrastructure.policy.ResourceExistsPolicy;
import it.tarczynski.jmolecules.reservation.infrastructure.policy.TimeSlotExistsPolicy;
import it.tarczynski.jmolecules.reservation.infrastructure.repository.PostgresReservationRepository;
import it.tarczynski.jmolecules.reservation.infrastructure.validation.CompoundReservationValidator;
import it.tarczynski.jmolecules.shared.EventBus;
import it.tarczynski.jmolecules.shared.TimeMachine;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotRepository;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
class ReservationFacadeConfig {

    @Bean
    ReservationCommandFacade reservationFacade(
            TimeMachine timeMachine,
            ReservationRepository reservationRepository,
            ReservableResourceRepository resourceRepository,
            TimeSlotRepository timeSlotRepository,
            EventBus eventBus
    ) {
        final var creationPolicies = creationPolicies(resourceRepository, timeSlotRepository);
        final var validator = reservationValidator(creationPolicies);
        return new ReservationCommandFacade(timeMachine, reservationRepository, validator, eventBus);
    }

    @Bean
    ReservationRepository reservationRepository(DSLContext context) {
        return new PostgresReservationRepository(context);
    }

    private List<ReservationCreationPolicy> creationPolicies(
            ReservableResourceRepository resourceRepository,
            TimeSlotRepository timeSlotRepository
    ) {
        return List.of(
                new ResourceExistsPolicy(resourceRepository),
                new TimeSlotExistsPolicy(timeSlotRepository)
        );
    }

    private ReservationValidator reservationValidator(List<ReservationCreationPolicy> creationPolicies) {
        return new CompoundReservationValidator(creationPolicies);
    }
}
