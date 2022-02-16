package it.tarczynski.jmolecules.timeslot.domain.exception;

import it.tarczynski.jmolecules.shared.domain.exception.ExhaustedException;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;

public class TimeSlotExhaustedException extends ExhaustedException {

    public TimeSlotExhaustedException(TimeSlotId id) {
        super("TimeSlot [%s] already exhausted".formatted(id.value()));
    }
}
