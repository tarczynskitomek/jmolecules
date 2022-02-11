package it.tarczynski.jmolecules.reservation.application.dto;

import it.tarczynski.jmolecules.reservation.domain.view.ReservationView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationReadModelResponse {

    private final UUID id;
    private final Instant createdAt;
    private final Instant placedAt;
    private final Instant confirmedAt;
    private final ReservableResourceViewResponse resource;
    private final TimeSlotViewResponse timeSlot;

    public static ReservationReadModelResponse from(ReservationView view) {
        return ReservationReadModelResponse.builder()
                .id(view.id().value())
                .createdAt(view.createdAt())
                .placedAt(view.placedAt())
                .confirmedAt(view.confirmedAt())
                .resource(new ReservableResourceViewResponse(view.resource().id().value()))
                .timeSlot(new TimeSlotViewResponse(view.timeSlot().id().value()))
                .build();
    }
}
