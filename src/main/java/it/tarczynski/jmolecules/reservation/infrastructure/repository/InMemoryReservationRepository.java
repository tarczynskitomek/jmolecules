package it.tarczynski.jmolecules.reservation.infrastructure.repository;

import it.tarczynski.jmolecules.reservation.domain.Reservation;
import it.tarczynski.jmolecules.reservation.domain.ReservationId;
import it.tarczynski.jmolecules.reservation.domain.ReservationRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryReservationRepository implements ReservationRepository {

    private static final Map<ReservationId, Reservation> RESERVATIONS = new ConcurrentHashMap<>();

    @Override
    public Reservation save(Reservation reservation) {
        RESERVATIONS.put(reservation.id(), reservation);
        return reservation;
    }

    @Override
    public Reservation get(ReservationId id) {
        return RESERVATIONS.get(id);
    }
}
