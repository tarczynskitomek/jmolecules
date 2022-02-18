package it.tarczynski.jmolecules.timeslot.domain;

import it.tarczynski.jmolecules.shared.domain.ReservableTokens;
import it.tarczynski.jmolecules.timeslot.domain.exception.TimeSlotExhaustedException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.Identity;

import java.time.Instant;

@AggregateRoot
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TimeSlot {

    @Identity
    private final TimeSlotId id;
    private final Instant from;
    private final Instant to;
    private final ReservableTokens tokens;

    public TimeSlotId id() {
        return id;
    }

    public Instant from() {
        return from;
    }

    public Instant to() {
        return to;
    }

    public ReservableTokens tokens() {
        return tokens;
    }

    public TimeSlot reserve() {
        if (!tokens.hasAvailableTokens()) throw new TimeSlotExhaustedException(id);
        return new TimeSlot(id, from, to, tokens.reserveOne());
    }

    public TimeSlot confirmReservation() {
        if (!tokens.hasReserved()) throw new IllegalStateException("Not reserved");
        return new TimeSlot(id, from, to, tokens.confirmOne());
    }
}
