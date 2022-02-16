package it.tarczynski.jmolecules.reservation.domain.view;

import it.tarczynski.jmolecules.shared.domain.ReservableTokens;
import it.tarczynski.jmolecules.timeslot.domain.TimeSlotId;

public record TimeSlotView(TimeSlotId id, ReservableTokens tokens) {
}
