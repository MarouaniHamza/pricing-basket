package dev.unify.service.impl;

import dev.unify.model.Discount;
import dev.unify.model.Item;
import dev.unify.utils.BasketUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleDiscountOfferTest {

    @Test
    void applyShouldReturnNoDiscountWhenNoAppleAndOfferIs1OPercentOnApples() {
        //Given
        var basket = BasketUtils.fromList(List.of("Soup", "Bread"));
        var simpleDiscountOffer = SimpleDiscountOffer.builder()
                .label("Apples 10% off")
                .appliedTo(Item.APPLES)
                .percentage(BigDecimal.valueOf(0.1))
                .build();
        //When
        var actualDiscount = simpleDiscountOffer.apply(basket);
        //Then
        var expectedDiscount = Discount.builder()
                .label("Apples 10% off")
                .amount(BigDecimal.valueOf(0))
                .build();
        assertEquals(actualDiscount, expectedDiscount);
    }

    @Test
    void applyShouldReturnDiscount0Point1WhenOfferIs10PercentOnApples() {
        //Given
        var basket = BasketUtils.fromList(List.of("Apples", "Soup", "Bread"));
        var simpleDiscountOffer = SimpleDiscountOffer.builder()
                .label("Apples 10% off")
                .appliedTo(Item.APPLES)
                .percentage(BigDecimal.valueOf(0.1))
                .build();
        //When
        var actualDiscount = simpleDiscountOffer.apply(basket);
        //Then
        var expectedDiscount = Discount.builder()
                .label("Apples 10% off")
                .amount(BigDecimal.valueOf(0.1))
                .build();
        assertEquals(actualDiscount, expectedDiscount);
    }
}
