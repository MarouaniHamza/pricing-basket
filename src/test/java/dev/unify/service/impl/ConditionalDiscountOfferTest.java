package dev.unify.service.impl;

import dev.unify.model.Discount;
import dev.unify.model.Item;
import dev.unify.utils.BasketUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConditionalDiscountOfferTest {

    @Test
    void applyShouldReturn0Point4WhenOfferIsHalfBreadFor2Soup() {
        //Given
        var basket = BasketUtils.fromList(List.of("Soup", "Soup", "Bread"));
        var conditionalDiscountOffer = ConditionalDiscountOffer.builder()
                .label("2 tins of soup and get a loaf of bread")
                .appliedTo(Item.BREAD)
                .percentage(BigDecimal.valueOf(0.5))
                .baseItem(Item.SOUP)
                .quantity(2)
                .build();
        //When
        var actualDiscount = conditionalDiscountOffer.apply(basket);
        //Then
        var expectedDiscount = Discount.builder()
                .label("2 tins of soup and get a loaf of bread")
                .amount(BigDecimal.valueOf(0.4))
                .build();
        assertEquals(actualDiscount, expectedDiscount);
    }

    @Test
    void applyShouldReturn0Point4WhenOfferIsHalfBreadFor2SoupWithPlusSoup() {
        //Given
        var basket = BasketUtils.fromList(List.of("Soup", "Soup", "Bread", "Soup"));
        var conditionalDiscountOffer = ConditionalDiscountOffer.builder()
                .label("2 tins of soup and get a loaf of bread")
                .appliedTo(Item.BREAD)
                .percentage(BigDecimal.valueOf(0.5))
                .baseItem(Item.SOUP)
                .quantity(2)
                .build();
        //When
        var actualDiscount = conditionalDiscountOffer.apply(basket);
        //Then
        var expectedDiscount = Discount.builder()
                .label("2 tins of soup and get a loaf of bread")
                .amount(BigDecimal.valueOf(0.4))
                .build();
        assertEquals(actualDiscount, expectedDiscount);
    }

    @Test
    void applyShouldReturn0WhenOfferIsHalfBreadFor2SoupWithOneSoup() {
        //Given
        var basket = BasketUtils.fromList(List.of("Soup", "Bread"));
        var conditionalDiscountOffer = ConditionalDiscountOffer.builder()
                .label("2 tins of soup and get a loaf of bread")
                .appliedTo(Item.BREAD)
                .percentage(BigDecimal.valueOf(0.5))
                .baseItem(Item.SOUP)
                .quantity(2)
                .build();
        //When
        var actualDiscount = conditionalDiscountOffer.apply(basket);
        //Then
        var expectedDiscount = Discount.builder()
                .label("2 tins of soup and get a loaf of bread")
                .amount(BigDecimal.valueOf(0))
                .build();
        assertEquals(actualDiscount, expectedDiscount);
    }
}
