package it.tarczynski.jmolecules.timeslot.application.dto;

import it.tarczynski.jmolecules.timeslot.domain.TimeSlot;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeSlotResponse {

    private final UUID id;
    private final Instant from;
    private final Instant to;

    public static TimeSlotResponse from(TimeSlot timeSlot) {
        return new TimeSlotResponse(timeSlot.id().value(), timeSlot.from(), timeSlot.to());
    }
}
