package dev.unify.service.impl;

import dev.unify.model.Discount;
import dev.unify.model.PurchaseSummary;
import dev.unify.service.PurchaseSummaryPrinter;

import java.util.logging.Logger;


public class ConsolePurchaseSummaryPrinter implements PurchaseSummaryPrinter {

    private static final Logger log = Logger.getLogger(ConsolePurchaseSummaryPrinter.class.getName());

    @Override
    public void print(final PurchaseSummary purchaseSummary) {
        final var discountsString = purchaseSummary.getDiscounts().stream()
                .map(Discount::toString)
                .reduce((discount, discount1) -> discount + "\n" + discount1)
                .orElse("(No offers available)");
        final var message = """
                Subtotal: £%s
                %s
                Total: £%s
                """.formatted(purchaseSummary.getSubTotal(), discountsString, purchaseSummary.getTotal());
        log.info(message);
    }
}
