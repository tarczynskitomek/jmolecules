package it.tarczynski.jmolecules.reservation.infrastructure.validation;

import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.reservation.domain.ReservationCreationPolicy;
import it.tarczynski.jmolecules.reservation.domain.ReservationValidator;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@AllArgsConstructor
public class CompoundReservationValidator implements ReservationValidator {

    private static final Logger LOG = LoggerFactory.getLogger(CompoundReservationValidator.class);

    private final List<ReservationCreationPolicy> creationPolicies;

    @Override
    public void canCreateFor(ResourceId resourceId, TimeSlotId timeSlotId) {
        LOG.info("Validating resource [{}] and time slot [{}]", resourceId.value(), timeSlotId.value());
        creationPolicies.forEach(policy -> policy.canCreateFor(resourceId, timeSlotId));
        LOG.info("Validation passed, reservation can be created");
    }
}
