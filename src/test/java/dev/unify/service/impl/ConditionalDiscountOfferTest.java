package dev.unify.service.impl;

import dev.unify.model.Discount;
import dev.unify.model.Item;
import dev.unify.utils.BasketUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConditionalDiscountOfferTest {

    private static final String SOUP = "Soup";
    private static final String BREAD = "Bread";
    private static final String TINS_OF_SOUP_AND_GET_A_LOAF_OF_BREAD = "2 tins of soup and get a loaf of bread";

    @Test
    void applyShouldReturn0Point4WhenOfferIsHalfBreadFor2Soup() {
        //Given
        var basket = BasketUtils.fromList(List.of(SOUP, SOUP, BREAD));
        var conditionalDiscountOffer = ConditionalDiscountOffer.builder()
                .label(TINS_OF_SOUP_AND_GET_A_LOAF_OF_BREAD)
                .appliedTo(Item.BREAD)
                .percentage(BigDecimal.valueOf(0.5))
                .baseItem(Item.SOUP)
                .quantity(2)
                .build();
        //When
        var actualDiscount = conditionalDiscountOffer.apply(basket);
        //Then
        var expectedDiscount = Discount.builder()
                .label(TINS_OF_SOUP_AND_GET_A_LOAF_OF_BREAD)
                .amount(BigDecimal.valueOf(0.4))
                .build();
        assertEquals(actualDiscount, expectedDiscount);
    }

    @Test
    void applyShouldReturn0Point4WhenOfferIsHalfBreadFor2SoupWithPlusSoup() {
        //Given
        var basket = BasketUtils.fromList(List.of(SOUP, SOUP, BREAD, SOUP));
        var conditionalDiscountOffer = ConditionalDiscountOffer.builder()
                .label(TINS_OF_SOUP_AND_GET_A_LOAF_OF_BREAD)
                .appliedTo(Item.BREAD)
                .percentage(BigDecimal.valueOf(0.5))
                .baseItem(Item.SOUP)
                .quantity(2)
                .build();
        //When
        var actualDiscount = conditionalDiscountOffer.apply(basket);
        //Then
        var expectedDiscount = Discount.builder()
                .label(TINS_OF_SOUP_AND_GET_A_LOAF_OF_BREAD)
                .amount(BigDecimal.valueOf(0.4))
                .build();
        assertEquals(actualDiscount, expectedDiscount);
    }

    @Test
    void applyShouldReturn0WhenOfferIsHalfBreadFor2SoupWithOneSoup() {
        //Given
        var basket = BasketUtils.fromList(List.of(SOUP, BREAD));
        var conditionalDiscountOffer = ConditionalDiscountOffer.builder()
                .label(TINS_OF_SOUP_AND_GET_A_LOAF_OF_BREAD)
                .appliedTo(Item.BREAD)
                .percentage(BigDecimal.valueOf(0.5))
                .baseItem(Item.SOUP)
                .quantity(2)
                .build();
        //When
        var actualDiscount = conditionalDiscountOffer.apply(basket);
        //Then
        var expectedDiscount = Discount.builder()
                .label(TINS_OF_SOUP_AND_GET_A_LOAF_OF_BREAD)
                .amount(BigDecimal.valueOf(0))
                .build();
        assertEquals(actualDiscount, expectedDiscount);
    }
}
