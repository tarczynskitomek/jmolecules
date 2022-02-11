package it.tarczynski.jmolecules.reservation.domain;

import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;

public interface ReservationValidator {

    void canCreateFor(ResourceId resourceId, TimeSlotId timeSlotId);
}
