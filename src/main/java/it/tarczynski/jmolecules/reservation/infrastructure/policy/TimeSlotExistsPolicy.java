package it.tarczynski.jmolecules.reservation.infrastructure.policy;

import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.reservation.domain.ReservationCreationPolicy;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotRepository;
import it.tarczynski.jmolecules.timeslot.infrastructure.exception.ExpectedTimeSlotNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TimeSlotExistsPolicy implements ReservationCreationPolicy {

    private final TimeSlotRepository timeSlotRepository;

    @Override
    public void canCreateFor(ResourceId resourceId, TimeSlotId timeSlotId) {
        if (!timeSlotRepository.exists(timeSlotId)) {
            throw new ExpectedTimeSlotNotFoundException(timeSlotId);
        }
    }
}
