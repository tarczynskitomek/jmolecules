package it.tarczynski.jmolecules.reservation.infrastructure.repository;

import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.reservation.domain.Reservation;
import it.tarczynski.jmolecules.reservation.domain.ReservationId;
import it.tarczynski.jmolecules.reservation.domain.ReservationRepository;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;
import lombok.AllArgsConstructor;
import org.jmolecules.ddd.annotation.Repository;
import org.jooq.DSLContext;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static it.tarczynski.jmolecules.infrastructure.generated.tables.Reservations.RESERVATIONS;

@Repository
@AllArgsConstructor
public class PostgresReservationRepository implements ReservationRepository {

    private final DSLContext context;

    @Override
    public Reservation save(Reservation reservation) {
        final var record = context.newRecord(RESERVATIONS);
        record.setId(reservation.id().value().toString());
        record.setResourceId(reservation.resourceId().value().toString());
        record.setTimeslotId(reservation.resourceId().value().toString());
        record.setCreatedAt(reservation.createdAt().atOffset(ZoneOffset.UTC));
        record.store();
        return reservation;
    }

    @Override
    public Reservation get(ReservationId id) {
        final var record = context.fetchOne(RESERVATIONS, RESERVATIONS.ID.eq(id.value().toString()));
        if (record == null) {
            throw new IllegalStateException("Reservation [%s] not found".formatted(id.value()));
        }
        return new Reservation(
                new ReservationId(UUID.fromString(record.getId())),
                new ResourceId(UUID.fromString(record.getResourceId())),
                new TimeSlotId(UUID.fromString(record.getTimeslotId())),
                record.getCreatedAt().toInstant(),
                toInstantNullable(record.getPlacedAt()),
                toInstantNullable(record.getConfirmedAt())
        );
    }

    private Instant toInstantNullable(OffsetDateTime nullableTime) {
        return nullableTime != null ? nullableTime.toInstant() : null;
    }
}
