package it.tarczynski.jmolecules.timeslot.application;

import it.tarczynski.jmolecules.shared.domain.ReservableTokens;
import it.tarczynski.jmolecules.timeslot.application.dto.CreateTimeSlotRequest;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlot;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
public class TimeSlotFacade {

    private static final Logger LOG = LoggerFactory.getLogger(TimeSlotFacade.class);

    private final TimeSlotRepository timeSlotRepository;

    @Transactional
    public TimeSlot create(CreateTimeSlotRequest request) {
        final var id = TimeSlotId.next();
        final var from = request.getFrom();
        final var to = request.getTo();
        final var tokens = new ReservableTokens(request.getTokens());
        final var timeSlot = new TimeSlot(id, from, to, tokens);
        LOG.info("Creating time slot with id [{}], from [{}] to [{}], tokens: [{}]", id.value(), from, to, tokens);
        return timeSlotRepository.save(timeSlot);
    }
}
