package it.tarczynski.jmolecules.reservation.application.dto;

import it.tarczynski.jmolecules.reservation.domain.view.ReservationView;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationsReadModelResponse {

    private final List<ReservationReadModelResponse> reservations;

    public static ReservationsReadModelResponse from(List<ReservationView> reservations) {
        return new ReservationsReadModelResponse(
                reservations.stream()
                        .map(ReservationReadModelResponse::from)
                        .toList()
        );
    }
}
