package it.tarczynski.jmolecules.reservable.infrastructure.exception;

import it.tarczynski.jmolecules.reservable.domain.ResourceId;
import it.tarczynski.jmolecules.shared.infrastructure.exception.PreconditionFailedException;

public class ExpectedResourceNotFoundException extends PreconditionFailedException {

    public ExpectedResourceNotFoundException(ResourceId id) {
        super("Precondition failed. Expected resource [%s] does not exist".formatted(id.value()));
    }
}
