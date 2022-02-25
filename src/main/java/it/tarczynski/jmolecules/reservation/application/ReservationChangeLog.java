package it.tarczynski.jmolecules.reservation.application;

import it.tarczynski.jmolecules.reservation.domain.ReservationId;
import it.tarczynski.jmolecules.shared.ChangeLogEntry;
import it.tarczynski.jmolecules.shared.ChangeLogRepository;
import it.tarczynski.jmolecules.shared.domain.ChangeLog;
import it.tarczynski.jmolecules.shared.domain.CommandId;
import it.tarczynski.jmolecules.shared.domain.Operation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservationChangeLog implements ChangeLog<ReservationId> {

    private final ChangeLogRepository changeLogRepository;

    @Override
    public void log(CommandId commandId,
                    ReservationId entityId,
                    Operation operation) {
        final var logEntry = new ChangeLogEntry(commandId, entityId, operation);
        changeLogRepository.save(logEntry);
    }
}
