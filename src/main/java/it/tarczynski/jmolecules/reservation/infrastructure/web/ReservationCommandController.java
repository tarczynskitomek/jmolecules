package it.tarczynski.jmolecules.reservation.infrastructure.web;

import it.tarczynski.jmolecules.reservation.application.ReservationCommandFacade;
import it.tarczynski.jmolecules.reservation.application.dto.ConfirmReservationRequest;
import it.tarczynski.jmolecules.reservation.application.dto.CreateReservationRequest;
import it.tarczynski.jmolecules.reservation.application.dto.PlaceReservationRequest;
import it.tarczynski.jmolecules.reservation.application.dto.ReservationResponse;
import it.tarczynski.jmolecules.reservation.domain.ReservationId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationCommandController {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationCommandController.class);

    private final ReservationCommandFacade reservationFacade;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse create(@RequestBody CreateReservationRequest request) {
        final var resourceId = request.resourceId();
        final var timeSlotId = request.timeSlotId();
        final var reservation = reservationFacade.create(resourceId, timeSlotId);
        return ReservationResponse.from(reservation);
    }

    @PutMapping(
            path = "/{reservationId}/place",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ReservationResponse place(@PathVariable UUID reservationId,
                                     @RequestBody PlaceReservationRequest request) {
        LOG.info("Received place reservation command [{}]", request.commandId());
        final var placed = reservationFacade.place(new ReservationId(reservationId));
        return ReservationResponse.from(placed);
    }

    @PutMapping(
            path = "/{reservationId}/confirm",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ReservationResponse confirm(@PathVariable UUID reservationId,
                                       @RequestBody ConfirmReservationRequest request) {
        LOG.info("Received confirm reservation command [{}]", request.commandId());
        final var confirmed = reservationFacade.confirm(new ReservationId(reservationId));
        return ReservationResponse.from(confirmed);
    }
}
