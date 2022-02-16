package it.tarczynski.jmolecules.reservation.application.dto;

import it.tarczynski.jmolecules.reservation.domain.view.ReservableResourceReadModel;
import it.tarczynski.jmolecules.reservation.domain.view.ReservationView;
import it.tarczynski.jmolecules.reservation.domain.view.TimeSlotView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

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
    private final ReservableResourceReadModelResponse resource;
    private final TimeSlotReadModelResponse timeSlot;

    public static ReservationReadModelResponse from(ReservationView view) {
        return ReservationReadModelResponse.builder()
                .id(view.id().value())
                .createdAt(view.createdAt())
                .placedAt(view.placedAt())
                .confirmedAt(view.confirmedAt())
                .resource(toResourceResponse(view.resource()))
                .timeSlot(toTimeSlotResponse(view.timeSlot()))
                .build();
    }

    @NotNull
    private static TimeSlotReadModelResponse toTimeSlotResponse(TimeSlotView timeSlotView) {
        return new TimeSlotReadModelResponse(
                timeSlotView.id().value(),
                timeSlotView.tokens().available(),
                timeSlotView.tokens().reserved(),
                timeSlotView.tokens().taken()
        );
    }

    @NotNull
    private static ReservableResourceReadModelResponse toResourceResponse(ReservableResourceReadModel resourceView) {
        return new ReservableResourceReadModelResponse(
                resourceView.id().value(),
                resourceView.tokens().available(),
                resourceView.tokens().reserved(),
                resourceView.tokens().taken()
        );
    }
}
