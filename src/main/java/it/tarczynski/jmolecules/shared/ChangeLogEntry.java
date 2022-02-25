package it.tarczynski.jmolecules.shared;

import it.tarczynski.jmolecules.reservation.domain.ReservationId;
import it.tarczynski.jmolecules.shared.domain.CommandId;
import it.tarczynski.jmolecules.shared.domain.Operation;

public record ChangeLogEntry(CommandId commandId, ReservationId entityId, Operation operation) {
}
