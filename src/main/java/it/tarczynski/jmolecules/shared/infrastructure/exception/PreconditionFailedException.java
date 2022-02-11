package it.tarczynski.jmolecules.shared.infrastructure.exception;

public class PreconditionFailedException extends RuntimeException {

    public PreconditionFailedException(String message) {
        super(message);
    }
}
