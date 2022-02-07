package it.tarczynski.jmolecules.reservation.application.dto;

import it.tarczynski.jmolecules.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class ReservationResponse {

    private final UUID id;
    private final UUID resourceId;
    private final UUID timeSlotId;
    private final Instant createdAt;
    private final Instant placedAt;
    private final Instant confirmedAt;

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.id().value(),
                reservation.resourceId().value(),
                reservation.timeSlotId().value(),
                reservation.createdAt(),
                reservation.placedAt(),
                reservation.confirmedAt()
        );
    }
}
