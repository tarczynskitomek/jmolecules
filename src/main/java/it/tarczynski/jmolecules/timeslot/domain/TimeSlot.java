package it.tarczynski.jmolecules.timeslot.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.time.Instant;

@AggregateRoot
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TimeSlot {

    @Identity
    private final TimeSlotId id;
    private final Instant from;
    private final Instant to;

    public TimeSlotId id() {
        return id;
    }
}
