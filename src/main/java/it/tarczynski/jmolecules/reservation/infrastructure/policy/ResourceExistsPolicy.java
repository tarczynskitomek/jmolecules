package it.tarczynski.jmolecules.reservation.infrastructure.policy;

import it.tarczynski.jmolecules.reservable.domain.ReservableResourceRepository;
import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.reservable.infrastructure.exception.ExpectedResourceNotFoundException;
import it.tarczynski.jmolecules.reservation.domain.ReservationCreationPolicy;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResourceExistsPolicy implements ReservationCreationPolicy {

    private final ReservableResourceRepository resourceRepository;

    @Override
    public void canCreateFor(ResourceId resourceId, TimeSlotId timeSlotId) {
        if (!resourceRepository.exists(resourceId)) {
            throw new ExpectedResourceNotFoundException(resourceId);
        }
    }
}
