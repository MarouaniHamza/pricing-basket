package dev.unify.service.impl;

import dev.unify.model.Basket;
import dev.unify.model.Discount;
import dev.unify.model.PurchaseSummary;
import dev.unify.service.Offer;
import dev.unify.service.PurchaseSummaryService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PurchaseSummaryServiceImpl implements PurchaseSummaryService {

    @Override
    public PurchaseSummary getPurchaseSummary(final Basket basket, final List<Offer> offers) {
        final var subTotal = getSubTotal(basket).setScale(2, RoundingMode.UP);
        final var discounts = getDiscounts(basket, offers);
        final var totalDiscounts = getTotalDiscount(discounts);
        final var total = getSubTotal(basket).subtract(totalDiscounts).setScale(2, RoundingMode.UP);
        return PurchaseSummary.builder()
                .subTotal(subTotal)
                .discounts(discounts)
                .total(total)
                .build();
    }

    private List<Discount> getDiscounts(final Basket basket, final List<Offer> offers) {
        return offers.stream()
                .map(offer -> offer.apply(basket))
                .filter(discount -> BigDecimal.ZERO.compareTo(discount.getAmount()) != 0)
                .toList();
    }

    private BigDecimal getSubTotal(final Basket basket) {
        return basket
                .getContent()
                .entrySet()
                .stream()
                .map(
                        basketEntry -> basketEntry
                                .getKey()
                                .getUnitPrice()
                                .multiply(BigDecimal.valueOf(basketEntry.getValue()))
                )
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private BigDecimal getTotalDiscount(final List<Discount> discounts) {
        return discounts.stream()
                .map(Discount::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }
}
