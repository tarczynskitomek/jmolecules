package it.tarczynski.jmolecules.shared;

import java.time.Instant;

public interface TimeMachine {

    default Instant now() {
        return Instant.now();
    }
}
