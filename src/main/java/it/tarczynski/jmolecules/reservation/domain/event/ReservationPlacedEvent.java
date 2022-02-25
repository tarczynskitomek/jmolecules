package it.tarczynski.jmolecules.reservation.domain.event;

import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.reservation.domain.Reservation;
import it.tarczynski.jmolecules.reservation.domain.ReservationId;
import it.tarczynski.jmolecules.shared.domain.CommandId;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
public class ReservationPlacedEvent {

    private final Reservation reservation;

    private final CommandId commandId;

    public CommandId commandId() {
        return commandId;
    }

    public ReservationId reservationId() {
        return reservation.id();
    }

    public TimeSlotId timeSlotId() {
        return reservation.timeSlotId();
    }

    public ResourceId resourceId() {
        return reservation.resourceId();
    }

    public Instant placedAt() {
        return reservation.placedAt();
    }
}
