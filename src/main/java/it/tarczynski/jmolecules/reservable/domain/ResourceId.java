package it.tarczynski.jmolecules.reservable.domain;

import java.util.UUID;

public record ResourceId(UUID value) {
    public static ResourceId next() {
        return new ResourceId(UUID.randomUUID());
    }
}
