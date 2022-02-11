package it.tarczynski.jmolecules.reservation.infrastructure.web;

import it.tarczynski.jmolecules.reservation.application.dto.ReservationReadModelResponse;
import it.tarczynski.jmolecules.reservation.application.dto.ReservationsReadModelResponse;
import it.tarczynski.jmolecules.reservation.domain.ReservationId;
import it.tarczynski.jmolecules.reservation.domain.view.ReservationReadModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationReadModelController {

    private final ReservationReadModelRepository readRepository;

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReservationReadModelResponse getBy(@PathVariable UUID id) {
        final var reservation = readRepository.getBy(new ReservationId(id));
        return ReservationReadModelResponse.from(reservation);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ReservationsReadModelResponse getReservations() {
        final var reservations = readRepository.findAll();
        return ReservationsReadModelResponse.from(reservations);
    }
}
