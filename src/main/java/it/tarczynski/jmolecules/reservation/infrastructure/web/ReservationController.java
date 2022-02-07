package it.tarczynski.jmolecules.reservation.infrastructure.web;

import it.tarczynski.jmolecules.reservation.application.ReservationFacade;
import it.tarczynski.jmolecules.reservation.application.dto.CreateReservationRequest;
import it.tarczynski.jmolecules.reservation.application.dto.ReservationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationController {

    private final ReservationFacade reservationFacade;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@RequestBody CreateReservationRequest request) {
        final var resourceId = request.resourceId();
        final var timeSlotId = request.timeSlotId();
        final var reservation = reservationFacade.create(resourceId, timeSlotId);
        return ReservationResponse.from(reservation);
    }
}
