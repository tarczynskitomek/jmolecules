package it.tarczynski.jmolecules.reservation.application.dto;

import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateReservationRequest {

    private UUID resourceId;
    private UUID timeSlotId;

    public ResourceId resourceId() {
        return new ResourceId(resourceId);
    }

    public TimeSlotId timeSlotId() {
        return new TimeSlotId(timeSlotId);
    }
}
