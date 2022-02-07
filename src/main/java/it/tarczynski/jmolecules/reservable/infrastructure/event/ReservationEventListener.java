package it.tarczynski.jmolecules.reservable.infrastructure.event;

import it.tarczynski.jmolecules.reservable.application.ReservationEventHandler;
import it.tarczynski.jmolecules.reservation.domain.event.ReservationCreatedEvent;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;

@AllArgsConstructor
public class ReservationEventListener {

    private final ReservationEventHandler handler;

    @EventListener
    public void on(ReservationCreatedEvent event) {
        handler.handle(event);
    }
}
