package it.tarczynski.jmolecules.reservation.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
public class TimeSlotViewResponse {

    private final UUID id;
}
