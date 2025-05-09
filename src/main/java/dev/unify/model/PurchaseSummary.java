package dev.unify.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode
@Builder
@Getter
public class PurchaseSummary {

    private final BigDecimal subTotal;

    private final List<Discount> discounts;

    private final BigDecimal total;
}
