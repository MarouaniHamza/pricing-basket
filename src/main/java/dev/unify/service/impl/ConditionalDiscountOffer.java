package dev.unify.service.impl;

import dev.unify.model.Basket;
import dev.unify.model.Discount;
import dev.unify.model.Item;
import dev.unify.service.Offer;
import dev.unify.utils.BasketUtils;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class ConditionalDiscountOffer implements Offer {

    private final String label;

    private final Item appliedTo;

    private final BigDecimal percentage;

    private final Item baseItem;

    private final int quantity;

    @Override
    public Discount apply(final Basket basket) {
        final var numberOfItems = BasketUtils.getItemCount(this.baseItem, basket);
        if (numberOfItems < quantity) {
            return Discount.builder()
                    .label(this.label)
                    .amount(BigDecimal.ZERO)
                    .build();
        }
        final var numberOfDiscounts = BigDecimal.valueOf(numberOfItems / quantity);
        final var newItemValue = this.percentage.multiply(numberOfDiscounts);
        final var unitPrice = this.appliedTo.getUnitPrice();
        final var amount = newItemValue.multiply(unitPrice);
        return Discount.builder()
                .label(this.label)
                .amount(amount)
                .build();
    }
}
