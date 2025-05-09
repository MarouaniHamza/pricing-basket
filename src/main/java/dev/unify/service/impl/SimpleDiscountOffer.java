package dev.unify.service.impl;

import dev.unify.model.Basket;
import dev.unify.model.Discount;
import dev.unify.model.Item;
import dev.unify.service.Offer;
import dev.unify.utils.BasketUtils;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class SimpleDiscountOffer implements Offer {

    private final String label;

    private final Item appliedTo;

    private final BigDecimal percentage;

    @Override
    public Discount apply(final Basket basket) {
        final var numberOfItems = BasketUtils.getItemCount(this.appliedTo, basket);
        final var unitPrice = this.appliedTo.getUnitPrice();
        final var discountUnitValue = percentage.multiply(unitPrice);
        final var amount = discountUnitValue.multiply(BigDecimal.valueOf(numberOfItems));
        return Discount.builder()
                .label(this.label)
                .amount(amount)
                .build();
    }
}
