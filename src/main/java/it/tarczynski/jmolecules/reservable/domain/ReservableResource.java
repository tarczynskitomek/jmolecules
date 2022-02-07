package it.tarczynski.jmolecules.reservable.domain;

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
