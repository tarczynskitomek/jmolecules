package it.tarczynski.jmolecules.timeslot.infrastructure.exception;

import it.tarczynski.jmolecules.shared.infrastructure.exception.PreconditionFailedException;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;

public class ExpectedTimeSlotNotFoundException extends PreconditionFailedException {

    public ExpectedTimeSlotNotFoundException(TimeSlotId slotId) {
        super("Precondition failed. Expected time slot [%s] does not exist".formatted(slotId.value()));
    }
}
