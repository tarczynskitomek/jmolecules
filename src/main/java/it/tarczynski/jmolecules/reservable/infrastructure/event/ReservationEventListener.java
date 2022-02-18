package it.tarczynski.jmolecules.reservable.infrastructure.event;

import it.tarczynski.jmolecules.reservable.application.ReservationEventHandler;
import it.tarczynski.jmolecules.reservation.domain.event.ReservationConfirmedEvent;
import it.tarczynski.jmolecules.reservation.domain.event.ReservationCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class ReservationEventListener {

    private final ReservationEventHandler handler;

    @Transactional
    @EventListener
    public void on(ReservationCreatedEvent event) {
        handler.handle(event);
    }

    @Transactional
    @EventListener
    public void on(ReservationConfirmedEvent event) {
        handler.handle(event);
    }
}
