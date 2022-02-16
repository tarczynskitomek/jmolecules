package it.tarczynski.jmolecules.reservation.domain.view;

import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.shared.domain.ReservableTokens;

public record ReservableResourceReadModel(ResourceId id, ReservableTokens tokens) {
}
