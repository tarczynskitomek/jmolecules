package it.tarczynski.jmolecules.timeslot.application;

import it.tarczynski.jmolecules.reservation.domain.event.ReservationCreatedEvent;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class ReservationEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationEventHandler.class);

    private final TimeSlotRepository timeSlotRepository;

    public void handle(ReservationCreatedEvent event) {
        LOG.info("Reserving slot [{}]", event.timeSlotId().value());
        final var timeSlot = timeSlotRepository.get(event.timeSlotId());
        final var reserved = timeSlot.reserve();
        timeSlotRepository.update(reserved);
    }
}
