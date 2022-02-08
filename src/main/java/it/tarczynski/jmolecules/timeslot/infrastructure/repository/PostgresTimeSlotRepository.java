package it.tarczynski.jmolecules.timeslot.infrastructure.repository;

import it.tarczynski.jmolecules.infrastructure.generated.tables.Timeslots;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlot;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneOffset;

@AllArgsConstructor
public class PostgresTimeSlotRepository implements TimeSlotRepository {

    public static final Logger LOG = LoggerFactory.getLogger(PostgresTimeSlotRepository.class);

    private final DSLContext context;

    @Override
    public TimeSlot save(TimeSlot slot) {
        final var record = context.newRecord(Timeslots.TIMESLOTS);
        record.setId(slot.id().value().toString());
        record.setFromTime(slot.from().atOffset(ZoneOffset.UTC));
        record.setToTime(slot.to().atOffset(ZoneOffset.UTC));
        final var storedCount = record.store();
        LOG.info("Stored [{}] time slot records", storedCount);
        return slot;
    }
}
