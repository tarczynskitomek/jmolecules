package it.tarczynski.jmolecules.reservation.domain;

import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.jmolecules.ddd.annotation.AggregateRoot;

import java.time.Instant;

@AggregateRoot
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reservation {

    private final ReservationId id;
    private final ResourceId resourceId;
    private final TimeSlotId timeSlotId;
    private final Instant createdAt;
    private final Instant placedAt;
    private final Instant confirmedAt;

    private Reservation(ReservationId id, ResourceId resourceId, TimeSlotId timeSlotId, Instant createdAt) {
        this(id, resourceId, timeSlotId, createdAt, null, null);
    }

    public static Reservation create(
            ReservationId id,
            ResourceId resourceId,
            TimeSlotId timeSlotId,
            Instant createdAt
    ) {
        return new Reservation(id, resourceId, timeSlotId, createdAt);
    }

    public Reservation place(Instant placedAt) {
        return new Reservation(id, resourceId, timeSlotId, createdAt, placedAt, null);
    }

    public Reservation confirm(Instant confirmedAt) {
        return new Reservation(id, resourceId, timeSlotId, createdAt, placedAt, confirmedAt);
    }

    public boolean isPlaced() {
        return placedAt != null;
    }

    public boolean isConfirmed() {
        return confirmedAt != null;
    }

    public ReservationId id() {
        return id;
    }

    public TimeSlotId timeSlotId() {
        return timeSlotId;
    }

    public ResourceId resourceId() {
        return resourceId;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant confirmedAt() {
        return confirmedAt;
    }

    public Instant placedAt() {
        return placedAt;
    }
}
