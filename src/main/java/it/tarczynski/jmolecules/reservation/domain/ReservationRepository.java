package it.tarczynski.jmolecules.reservation.domain;

public interface ReservationRepository {
    Reservation save(Reservation reservation);
    Reservation update(Reservation reservation);
    Reservation get(ReservationId id);
}
