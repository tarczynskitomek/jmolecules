package it.tarczynski.jmolecules.reservation.infrastructure.event;

import it.tarczynski.jmolecules.reservation.domain.ReservationId;
import it.tarczynski.jmolecules.reservation.domain.event.ReservationPlacedEvent;
import it.tarczynski.jmolecules.shared.domain.ChangeLog;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;

import static it.tarczynski.jmolecules.reservation.domain.ReservationOperation.RESERVATION_PLACED;

@AllArgsConstructor
public class ReservationEventListener {

    private final ChangeLog<ReservationId> reservationChangeLog;

    @EventListener(ReservationPlacedEvent.class)
    public void handle(ReservationPlacedEvent event) {
        reservationChangeLog.log(event.commandId(), event.reservationId(), RESERVATION_PLACED);
    }
}
