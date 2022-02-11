package it.tarczynski.jmolecules.reservation.application.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ReservableResourceViewResponse {

    private final UUID id;
}
