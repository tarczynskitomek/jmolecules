package it.tarczynski.jmolecules.timeslot.application;

import it.tarczynski.jmolecules.timeslot.domain.TimeSlot;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@AllArgsConstructor
public class TimeSlotFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TimeSlotFacade.class);

    private final TimeSlotRepository timeSlotRepository;

    @Transactional
    public TimeSlot create(Instant from, Instant to) {
        final var id = TimeSlotId.next();
        final var timeSlot = new TimeSlot(id, from, to);
        LOG.info("Creating time slot with id [{}], from [{}] to [{}]", id.value(), from, to);
        return timeSlotRepository.save(timeSlot);
    }
}
