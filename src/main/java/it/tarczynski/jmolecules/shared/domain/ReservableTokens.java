package it.tarczynski.jmolecules.shared.domain;

import lombok.AllArgsConstructor;
import lombok.ToString;
import org.jmolecules.ddd.annotation.ValueObject;

@ToString
@ValueObject
@AllArgsConstructor
public class ReservableTokens {

    private final int available;
    private final int reserved;
    private final int taken;

    public ReservableTokens(int available) {
        this(available, 0, 0);
    }

    public ReservableTokens reserveOne() {
        if (available == 0) throw new IllegalStateException("Already exhausted");
        return new ReservableTokens(available - 1, reserved + 1, taken);
    }

    public ReservableTokens confirmOne() {
        if (reserved == 0) throw new IllegalStateException("No tokens reserved");
        return new ReservableTokens(available, reserved - 1, taken + 1);
    }

    public boolean hasAvailableTokens() {
        return available > 0;
    }

    public int available() {
        return available;
    }

    public int reserved() {
        return reserved;
    }

    public int taken() {
        return taken;
    }

    public boolean hasReserved() {
        return reserved > 0;
    }
}
