package it.tarczynski.jmolecules.timeslot.application.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class CreateTimeSlotRequest {

    private Instant from;
    private Instant to;
    private Integer tokens;
}
