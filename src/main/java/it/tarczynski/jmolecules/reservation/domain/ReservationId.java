package it.tarczynski.jmolecules.reservation.domain;

import java.util.UUID;

public record ReservationId(UUID value) {

    public static ReservationId next() {
        return new ReservationId(UUID.randomUUID());
    }
}
