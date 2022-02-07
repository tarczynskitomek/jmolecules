package it.tarczynski.jmolecules.reservable.infrastructure.web;

import it.tarczynski.jmolecules.reservable.application.dto.CreateReservableResourceRequest;
import it.tarczynski.jmolecules.reservable.application.dto.ReservableResourceResponse;
import it.tarczynski.jmolecules.reservable.domain.ReservableTokens;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resources")
@AllArgsConstructor
public class ReservableResourceController {

    private final ReservableResourceFacade facade;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ReservableResourceResponse create(@RequestBody CreateReservableResourceRequest request) {
        final var tokens = new ReservableTokens(request.tokens());
        final var resource = facade.createWith(tokens);
        return ReservableResourceResponse.from(resource);
    }
}
