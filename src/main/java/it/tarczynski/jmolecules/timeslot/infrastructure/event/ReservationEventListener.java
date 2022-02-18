package it.tarczynski.jmolecules.timeslot.infrastructure.event;

import it.tarczynski.jmolecules.reservation.domain.event.ReservationConfirmedEvent;
import it.tarczynski.jmolecules.reservation.domain.event.ReservationCreatedEvent;
import it.tarczynski.jmolecules.timeslot.application.ReservationEventHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class ReservationEventListener {

    private final ReservationEventHandler eventHandler;

    @Transactional
    @EventListener(ReservationCreatedEvent.class)
    public void handle(ReservationCreatedEvent event) {
        eventHandler.handle(event);
    }

    @Transactional
    @EventListener(ReservationConfirmedEvent.class)
    public void handle(ReservationConfirmedEvent event) {
        eventHandler.handle(event);
    }
}
