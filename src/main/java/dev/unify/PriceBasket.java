package dev.unify;


import dev.unify.model.Item;
import dev.unify.service.impl.ConditionalDiscountOffer;
import dev.unify.service.impl.ConsolePurchaseSummaryPrinter;
import dev.unify.service.impl.PurchaseSummaryServiceImpl;
import dev.unify.service.impl.SimpleDiscountOffer;
import dev.unify.utils.BasketUtils;

import java.math.BigDecimal;
import java.util.List;

public class PriceBasket {

    public static void main(String[] args) {
        final var basket = BasketUtils.fromList(List.of(args));
        final var appleTenPercentDiscountOffer = SimpleDiscountOffer.builder()
                .label("Apples 10% off")
                .appliedTo(Item.APPLES)
                .percentage(BigDecimal.valueOf(0.1))
                .build();
        final var halfBreadForTwoSoupDiscountOffer = ConditionalDiscountOffer.builder()
                .label("2 tins of soup and get a loaf of bread")
                .appliedTo(Item.BREAD)
                .percentage(BigDecimal.valueOf(0.5))
                .baseItem(Item.SOUP)
                .quantity(2)
                .build();
        final var offers = List.of(appleTenPercentDiscountOffer, halfBreadForTwoSoupDiscountOffer);
        final var purchaseSummaryService = new PurchaseSummaryServiceImpl();
        final var purchaseSummary = purchaseSummaryService.getPurchaseSummary(basket, offers);
        final var purchaseSummaryPrinter = new ConsolePurchaseSummaryPrinter();
        purchaseSummaryPrinter.print(purchaseSummary);
    }
}
