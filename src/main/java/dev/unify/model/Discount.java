package dev.unify.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Builder
public class Discount {

    private final String label;

    private final BigDecimal amount;

    @Override
    public String toString() {
        return String.format("%s: -%s£", this.label, this.amount);
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Discount discount)) {
            return false;
        }
        return Objects.equals(label, discount.label) &&
                amount.compareTo(discount.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, amount);
    }
}
