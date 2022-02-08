package it.tarczynski.jmolecules.timeslot.domain;

import java.util.UUID;

public record TimeSlotId(UUID value) {

    public static TimeSlotId next() {
        return new TimeSlotId(UUID.randomUUID());
    }
}
