package dev.unify.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;

@Getter
public enum Item {
    SOUP("Soup", BigDecimal.valueOf(0.65)),
    BREAD("Bread", BigDecimal.valueOf(0.80)),
    MILK("Milk", BigDecimal.valueOf(1.3)),
    APPLES("Apples", BigDecimal.valueOf(1));

    private final String label;

    private final BigDecimal unitPrice;

    Item(final String label, final BigDecimal unitPrice) {
        this.label = label;
        this.unitPrice = unitPrice;
    }

    public static Item fromLabel(final String value) {
        return Arrays.stream(Item.values())
                .filter(item -> item.getLabel().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(value + " is not a valid item label"));
    }
}
