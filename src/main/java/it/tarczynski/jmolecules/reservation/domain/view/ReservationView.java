package it.tarczynski.jmolecules.reservation.domain.view;

import it.tarczynski.jmolecules.reservation.domain.ReservationId;

import java.time.Instant;

public record ReservationView(
        ReservationId id,
        Instant createdAt,
        Instant placedAt,
        Instant confirmedAt,
        TimeSlotView timeSlot,
        ReservableResourceReadModel resource) {
}
