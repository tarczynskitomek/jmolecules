package it.tarczynski.jmolecules.shared.infrastructure;

import it.tarczynski.jmolecules.shared.ChangeLogEntry;
import it.tarczynski.jmolecules.shared.ChangeLogRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;

@AllArgsConstructor
public class PostgresChangeLogRepository implements ChangeLogRepository {

    private final DSLContext context;

    @Override
    public ChangeLogEntry save(ChangeLogEntry logEntry) {
        throw new UnsupportedOperationException("TODO");
    }
}
