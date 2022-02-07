package it.tarczynski.jmolecules.reservable.application.dto;

import it.tarczynski.jmolecules.reservable.domain.ReservableResource;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservableResourceResponse {

    private final UUID id;
    private final int availableTokens;
    private final int reservedTokens;
    private final int takenTokens;

    public static ReservableResourceResponse from(ReservableResource resource) {
        return new ReservableResourceResponse(
                resource.id().value(),
                resource.tokens().available(),
                resource.tokens().reserved(),
                resource.tokens().taken()
        );
    }
}
