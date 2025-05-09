package dev.unify.service.impl;

import dev.unify.model.Discount;
import dev.unify.model.Item;
import dev.unify.model.PurchaseSummary;
import dev.unify.service.Offer;
import dev.unify.utils.BasketUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseSummaryServiceImplTest {

    private static final String APPLES = "Apples";
    private static final String MILK = "Milk";
    private static final String BREAD = "Bread";
    private static final String SOUP = "Soup";
    private static final String APPLES_10_OFF = "Apples 10% off";
    private static final String TINS_OF_SOUP_AND_GET_A_LOAF_OF_BREAD = "2 tins of soup and get a loaf of bread";
    private PurchaseSummaryServiceImpl purchaseSummaryService;

    @BeforeEach
    void setUp() {
        purchaseSummaryService = new PurchaseSummaryServiceImpl();
    }

    @Test
    void getPurchaseSummaryShouldReturnExpectedPurchaseSummaryWhenEntryIsApplesMilkBreadNoOffer() {
        //Given
        var basket = BasketUtils.fromList(List.of(APPLES, MILK, BREAD));
        List<Offer> offers = Collections.emptyList();
        List<Discount> discounts = Collections.emptyList();
        var expectedPurchaseSummary = PurchaseSummary.builder()
                .subTotal(BigDecimal.valueOf(3.1).setScale(2, RoundingMode.UP))
                .discounts(discounts)
                .total(BigDecimal.valueOf(3.1).setScale(2, RoundingMode.UP))
                .build();
        //When
        var actualPurchaseSummary = purchaseSummaryService.getPurchaseSummary(basket, offers);
        //Then
        assertEquals(expectedPurchaseSummary, actualPurchaseSummary);
    }

    @Test
    void getPurchaseSummaryShouldReturnExpectedPurchaseSummaryWhenEntryIsApplesMilkBreadWithOffer() {
        //Given
        var basket = BasketUtils.fromList(List.of(APPLES, MILK, BREAD));
        var appleTenPercentDiscountOffer = SimpleDiscountOffer.builder()
                .label(APPLES_10_OFF)
                .appliedTo(Item.APPLES)
                .percentage(BigDecimal.valueOf(0.1))
                .build();
        var halfBreadForTwoSoupDiscountOffer = ConditionalDiscountOffer.builder()
                .label(TINS_OF_SOUP_AND_GET_A_LOAF_OF_BREAD)
                .appliedTo(Item.BREAD)
                .percentage(BigDecimal.valueOf(0.5))
                .baseItem(Item.SOUP)
                .quantity(2)
                .build();
        var offers = List.of(appleTenPercentDiscountOffer, halfBreadForTwoSoupDiscountOffer);
        var discounts = List.of(
                Discount.builder()
                        .label(APPLES_10_OFF)
                        .amount(BigDecimal.valueOf(0.1))
                        .build()
        );
        var expectedPurchaseSummary = PurchaseSummary.builder()
                .subTotal(BigDecimal.valueOf(3.1).setScale(2, RoundingMode.UP))
                .discounts(discounts)
                .total(BigDecimal.valueOf(3.0).setScale(2, RoundingMode.UP))
                .build();
        //When
        var actualPurchaseSummary = purchaseSummaryService.getPurchaseSummary(basket, offers);
        //Then
        assertEquals(expectedPurchaseSummary, actualPurchaseSummary);
    }

    @Test
    void getPurchaseSummaryShouldReturnExpectedPurchaseSummaryWhenEntryIsSoupSoupBreadWithOffer() {
        //Given
        var basket = BasketUtils.fromList(List.of(SOUP, SOUP, BREAD));
        var appleTenPercentDiscountOffer = SimpleDiscountOffer.builder()
                .label(APPLES_10_OFF)
                .appliedTo(Item.APPLES)
                .percentage(BigDecimal.valueOf(0.1))
                .build();
        var halfBreadForTwoSoupDiscountOffer = ConditionalDiscountOffer.builder()
                .label(TINS_OF_SOUP_AND_GET_A_LOAF_OF_BREAD)
                .appliedTo(Item.BREAD)
                .percentage(BigDecimal.valueOf(0.5))
                .baseItem(Item.SOUP)
                .quantity(2)
                .build();
        var offers = List.of(appleTenPercentDiscountOffer, halfBreadForTwoSoupDiscountOffer);
        var discounts = List.of(
                Discount.builder()
                        .label(TINS_OF_SOUP_AND_GET_A_LOAF_OF_BREAD)
                        .amount(BigDecimal.valueOf(0.4))
                        .build()
        );
        var expectedPurchaseSummary = PurchaseSummary.builder()
                .subTotal(BigDecimal.valueOf(2.1).setScale(2, RoundingMode.UP))
                .discounts(discounts)
                .total(BigDecimal.valueOf(1.7).setScale(2, RoundingMode.UP))
                .build();
        //When
        var actualPurchaseSummary = purchaseSummaryService.getPurchaseSummary(basket, offers);
        //Then
        assertEquals(expectedPurchaseSummary, actualPurchaseSummary);
    }

    @Test
    void getPurchaseSummaryShouldReturnExpectedPurchaseSummaryWhenEntryIsApplesSoupSoupBreadWithOffer() {
        //Given
        var basket = BasketUtils.fromList(List.of(APPLES, SOUP, SOUP, BREAD, SOUP));
        var appleTenPercentDiscountOffer = SimpleDiscountOffer.builder()
                .label(APPLES_10_OFF)
                .appliedTo(Item.APPLES)
                .percentage(BigDecimal.valueOf(0.1))
                .build();
        var halfBreadForTwoSoupDiscountOffer = ConditionalDiscountOffer.builder()
                .label(TINS_OF_SOUP_AND_GET_A_LOAF_OF_BREAD)
                .appliedTo(Item.BREAD)
                .percentage(BigDecimal.valueOf(0.5))
                .baseItem(Item.SOUP)
                .quantity(2)
                .build();
        var offers = List.of(appleTenPercentDiscountOffer, halfBreadForTwoSoupDiscountOffer);
        var discounts = List.of(
                Discount.builder()
                        .label(APPLES_10_OFF)
                        .amount(BigDecimal.valueOf(0.1))
                        .build(),
                Discount.builder()
                        .label(TINS_OF_SOUP_AND_GET_A_LOAF_OF_BREAD)
                        .amount(BigDecimal.valueOf(0.4))
                        .build()
        );
        var expectedPurchaseSummary = PurchaseSummary.builder()
                .subTotal(BigDecimal.valueOf(3.75).setScale(2, RoundingMode.UP))
                .discounts(discounts)
                .total(BigDecimal.valueOf(3.25).setScale(2, RoundingMode.UP))
                .build();
        //When
        var actualPurchaseSummary = purchaseSummaryService.getPurchaseSummary(basket, offers);
        //Then
        assertEquals(expectedPurchaseSummary, actualPurchaseSummary);
    }
}