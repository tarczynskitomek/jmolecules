package it.tarczynski.jmolecules.timeslot.infrastructure.repository;

import it.tarczynski.jmolecules.shared.domain.ReservableTokens;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlot;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotRepository;
import it.tarczynski.jmolecules.timeslot.domain.exception.ExpectedTimeSlotNotFoundException;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

import static it.tarczynski.jmolecules.infrastructure.generated.tables.Timeslots.TIMESLOTS;

@AllArgsConstructor
public class PostgresTimeSlotRepository implements TimeSlotRepository {

    public static final Logger LOG = LoggerFactory.getLogger(PostgresTimeSlotRepository.class);

    private final DSLContext context;

    @Override
    public TimeSlot save(TimeSlot slot) {
        final var record = context.newRecord(TIMESLOTS);
        record.setId(slot.id().value().toString());
        record.setFromTime(slot.from().atOffset(ZoneOffset.UTC));
        record.setToTime(slot.to().atOffset(ZoneOffset.UTC));
        record.setTokensAvailable(slot.tokens().available());
        record.setTokensReserved(slot.tokens().reserved());
        record.setTokensTaken(slot.tokens().taken());
        final var storedCount = record.store();
        LOG.info("Stored [{}] time slot records", storedCount);
        return slot;
    }

    @Override
    public boolean exists(TimeSlotId id) {
        return context.fetchExists(TIMESLOTS, TIMESLOTS.ID.eq(id.value().toString()));
    }

    @Override
    public TimeSlot get(TimeSlotId id) {
        final var record = context.fetchOne(TIMESLOTS, TIMESLOTS.ID.eq(id.value().toString()));
        if (Objects.isNull(record)) {
            throw new ExpectedTimeSlotNotFoundException(id);
        }
        return new TimeSlot(
                new TimeSlotId(UUID.fromString(record.getId())),
                record.getFromTime().toInstant(),
                record.getToTime().toInstant(),
                new ReservableTokens(
                        record.getTokensAvailable(),
                        record.getTokensReserved(),
                        record.getTokensTaken()
                )
        );
    }

    @Override
    public TimeSlot update(TimeSlot slot) {
        final int recordsUpdated = context.update(TIMESLOTS)
                .set(TIMESLOTS.TOKENS_AVAILABLE, slot.tokens().available())
                .set(TIMESLOTS.TOKENS_RESERVED, slot.tokens().reserved())
                .set(TIMESLOTS.TOKENS_TAKEN, slot.tokens().taken())
                .where(TIMESLOTS.ID.eq(slot.id().value().toString()))
                .execute();
        LOG.info("Updated timeslot [{}], records changed [{}]", slot.id().value(), recordsUpdated);
        return null;
    }
}
