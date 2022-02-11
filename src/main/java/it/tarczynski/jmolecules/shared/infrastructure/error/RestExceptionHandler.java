package it.tarczynski.jmolecules.shared.infrastructure.error;

import it.tarczynski.jmolecules.shared.infrastructure.exception.PreconditionFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(PreconditionFailedException.class)
    public ResponseEntity<Errors> handle(PreconditionFailedException e) {
        LOG.warn("Processing request failed. Cause: [{}]", e.getMessage());
        return ResponseEntity
                .status(PRECONDITION_FAILED)
                .body(Errors.of(e.getMessage()));
    }
}
