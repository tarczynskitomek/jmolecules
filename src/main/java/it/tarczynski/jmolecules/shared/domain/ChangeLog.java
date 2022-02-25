package it.tarczynski.jmolecules.shared.domain;

public interface ChangeLog<ID> {

    void log(CommandId commandId, ID entityId, Operation operation);
}
