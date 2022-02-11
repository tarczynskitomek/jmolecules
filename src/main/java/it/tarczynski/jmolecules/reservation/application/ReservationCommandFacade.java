package it.tarczynski.jmolecules.reservation.application;

import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.reservation.domain.Reservation;
import it.tarczynski.jmolecules.reservation.domain.ReservationId;
import it.tarczynski.jmolecules.reservation.domain.ReservationRepository;
import it.tarczynski.jmolecules.reservation.domain.ReservationValidator;
import it.tarczynski.jmolecules.reservation.domain.event.ReservationConfirmedEvent;
import it.tarczynski.jmolecules.reservation.domain.event.ReservationCreatedEvent;
import it.tarczynski.jmolecules.reservation.domain.event.ReservationPlacedEvent;
import it.tarczynski.jmolecules.shared.EventBus;
import it.tarczynski.jmolecules.shared.TimeMachine;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class ReservationCommandFacade {

    public static final Logger LOG = LoggerFactory.getLogger(ReservationCommandFacade.class);

    private final TimeMachine timeMachine;
    private final ReservationRepository reservationRepository;
    private final ReservationValidator reservationValidator;
    private final EventBus eventBus;

    @Transactional
    public Reservation create(ResourceId resourceId, TimeSlotId timeSlotId) {
        reservationValidator.canCreateFor(resourceId, timeSlotId);
        LOG.info("Creating reservation for resource [{}] and timeslot [{}]", resourceId.value(), timeSlotId.value());
        final var id = ReservationId.next();
        final var createdAt = timeMachine.now();
        final var reservation = Reservation.create(id, resourceId, timeSlotId, createdAt);
        final var savedReservation = reservationRepository.save(reservation);
        eventBus.publish(new ReservationCreatedEvent(savedReservation));
        LOG.info("Reservation created");
        return savedReservation;
    }

    @Transactional
    public Reservation place(ReservationId id) {
        final var reservation = reservationRepository.get(id);
        final var placedAt = timeMachine.now();
        final var placed = reservation.place(placedAt);
        final var saved = reservationRepository.save(placed);
        eventBus.publish(new ReservationPlacedEvent(saved));
        return saved;
    }

    @Transactional
    public Reservation confirm(ReservationId id) {
        final var reservation = reservationRepository.get(id);
        final var confirmedAt = timeMachine.now();
        final var confirmed = reservation.confirm(confirmedAt);
        final var saved = reservationRepository.save(confirmed);
        eventBus.publish(new ReservationConfirmedEvent(saved));
        return saved;
    }
}
