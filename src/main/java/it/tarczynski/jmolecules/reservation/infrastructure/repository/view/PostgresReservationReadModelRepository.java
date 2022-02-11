package it.tarczynski.jmolecules.reservation.infrastructure.repository.view;

import it.tarczynski.jmolecules.infrastructure.generated.tables.records.ReservationsViewRecord;
import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.reservation.domain.ReservationId;
import it.tarczynski.jmolecules.reservation.domain.view.ReservableResourceView;
import it.tarczynski.jmolecules.reservation.domain.view.ReservationReadModelRepository;
import it.tarczynski.jmolecules.reservation.domain.view.ReservationView;
import it.tarczynski.jmolecules.reservation.domain.view.TimeSlotView;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;

import java.util.List;
import java.util.UUID;

import static it.tarczynski.jmolecules.infrastructure.generated.tables.ReservationsView.RESERVATIONS_VIEW;

@AllArgsConstructor
public class PostgresReservationReadModelRepository implements ReservationReadModelRepository {

    private final DSLContext context;

    @Override
    public ReservationView getBy(ReservationId reservationId) {
        final var view = context.fetchOne(RESERVATIONS_VIEW, RESERVATIONS_VIEW.ID.eq(reservationId.value().toString()));
        if (view == null) {
            throw new IllegalStateException("Read view does not exist for reservation [%s]".formatted(reservationId.value()));
        }
        return toView(view);
    }

    @Override
    public List<ReservationView> findAll() {
        return context.select().from(RESERVATIONS_VIEW).fetchInto(ReservationsViewRecord.class)
                .stream()
                .map(this::toView)
                .toList();
    }

    private ReservationView toView(ReservationsViewRecord record) {
        return new ReservationView(
                new ReservationId(UUID.fromString(record.getId())),
                record.getCreatedAt().toInstant(),
                record.getPlacedAt() != null ? record.getPlacedAt().toInstant() : null,
                record.getConfirmedAt() != null ? record.getConfirmedAt().toInstant() : null,
                new TimeSlotView(new TimeSlotId(UUID.fromString(record.getTimeslotId()))),
                new ReservableResourceView(new ResourceId(UUID.fromString(record.getResourceId())))
        );
    }
}
