package it.tarczynski.jmolecules.reservation.application.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReservableResourceReadModelResponse {

    private final UUID id;
    private final int availableTokens;
    private final int reservedTokens;
    private final int takenTokens;
}
