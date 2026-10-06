package com.forgemagic.api;

import java.util.Objects;

public record Money(long centavos) implements Comparable<Money> {
    public static final Money ZERO = new Money(0);

    public Money {
        if (centavos < 0) throw new IllegalArgumentException("money cannot be negative");
    }

    public Money plus(Money other) {
        Objects.requireNonNull(other, "other");
        return new Money(Math.addExact(centavos, other.centavos));
    }

    public Money minus(Money other) {
        Objects.requireNonNull(other, "other");
        if (other.centavos > centavos) throw new IllegalArgumentException("insufficient money");
        return new Money(centavos - other.centavos);
    }

    public static Money tmt(long wholeTmt) {
        if (wholeTmt < 0) throw new IllegalArgumentException("TMT cannot be negative");
        return new Money(Math.multiplyExact(wholeTmt, 100));
    }

    @Override public int compareTo(Money other) { return Long.compare(centavos, other.centavos); }
}
