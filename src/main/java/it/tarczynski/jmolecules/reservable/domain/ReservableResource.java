package it.tarczynski.jmolecules.reservable.domain;

import it.tarczynski.jmolecules.reservable.domain.exception.ReservableResourceExhaustedException;
import it.tarczynski.jmolecules.shared.domain.ReservableTokens;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.time.Instant;

@AggregateRoot
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ReservableResource {

    @Identity
    private final ResourceId id;
    private final ReservableTokens tokens;
    private final Instant createdAt;

    public ReservableResource reserve() {
        if (!tokens.hasAvailableTokens()) {
            throw new ReservableResourceExhaustedException(id);
        }
        return new ReservableResource(id, tokens.reserve(), createdAt);
    }

    public ResourceId id() {
        return id;
    }

    public ReservableTokens tokens() {
        return tokens;
    }

    public Instant createdAt() {
        return createdAt;
    }
}
