package it.tarczynski.jmolecules.shared;

public interface ChangeLogRepository {

    ChangeLogEntry save(ChangeLogEntry logEntry);
}
