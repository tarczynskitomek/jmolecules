package it.tarczynski.jmolecules.reservable.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jmolecules.ddd.annotation.ValueObject;

@ValueObject
@AllArgsConstructor
public class ReservableTokens {

    private final int available;
    private final int reserved;
    private final int taken;

    public ReservableTokens(int available) {
        this(available, 0, 0);
    }

    ReservableTokens reserve() {
        if (available == 0) throw new IllegalStateException("Already exhausted");
        return new ReservableTokens(available - 1, reserved + 1, taken);
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
}
