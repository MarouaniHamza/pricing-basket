package dev.unify.service.impl;

import dev.unify.model.Discount;
import dev.unify.model.Item;
import dev.unify.utils.BasketUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleDiscountOfferTest {

    private static final String APPLES_10_OFF = "Apples 10% off";
    private static final String SOUP = "Soup";
    private static final String BREAD = "Bread";
    private static final String APPLES = "Apples";

    @Test
    void applyShouldReturnNoDiscountWhenNoAppleAndOfferIs1OPercentOnApples() {
        //Given
        var basket = BasketUtils.fromList(List.of(SOUP, BREAD));
        var simpleDiscountOffer = SimpleDiscountOffer.builder()
                .label(APPLES_10_OFF)
                .appliedTo(Item.APPLES)
                .percentage(BigDecimal.valueOf(0.1))
                .build();
        //When
        var actualDiscount = simpleDiscountOffer.apply(basket);
        //Then
        var expectedDiscount = Discount.builder()
                .label(APPLES_10_OFF)
                .amount(BigDecimal.valueOf(0))
                .build();
        assertEquals(actualDiscount, expectedDiscount);
    }

    @Test
    void applyShouldReturnDiscount0Point1WhenOfferIs10PercentOnApples() {
        //Given
        var basket = BasketUtils.fromList(List.of(APPLES, SOUP, BREAD));
        var simpleDiscountOffer = SimpleDiscountOffer.builder()
                .label(APPLES_10_OFF)
                .appliedTo(Item.APPLES)
                .percentage(BigDecimal.valueOf(0.1))
                .build();
        //When
        var actualDiscount = simpleDiscountOffer.apply(basket);
        //Then
        var expectedDiscount = Discount.builder()
                .label(APPLES_10_OFF)
                .amount(BigDecimal.valueOf(0.1))
                .build();
        assertEquals(actualDiscount, expectedDiscount);
    }
}
