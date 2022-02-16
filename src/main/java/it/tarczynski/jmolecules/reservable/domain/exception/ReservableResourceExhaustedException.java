package it.tarczynski.jmolecules.reservable.domain.exception;

import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.shared.domain.exception.ExhaustedException;

public class ReservableResourceExhaustedException extends ExhaustedException {

    public ReservableResourceExhaustedException(ResourceId id) {
        super("Resource [%s] exhausted".formatted(id.value()));
    }
}
