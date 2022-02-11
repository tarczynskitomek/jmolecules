package it.tarczynski.jmolecules.reservation.domain.view;

import it.tarczynski.jmolecules.reservation.domain.ReservationId;

import java.util.List;

public interface ReservationReadModelRepository {

    ReservationView getBy(ReservationId reservationId);

    List<ReservationView> findAll();

}
