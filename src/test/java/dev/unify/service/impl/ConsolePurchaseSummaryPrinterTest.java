package dev.unify.service.impl;

import dev.unify.model.Discount;
import dev.unify.model.PurchaseSummary;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsolePurchaseSummaryPrinterTest {

    private Handler handler;

    private Logger logger;

    private ByteArrayOutputStream logs;

    @BeforeEach
    void setUpLog() {
        logs = new ByteArrayOutputStream();
        handler = new StreamHandler(logs, new SimpleFormatter());
        logger = Logger.getLogger(ConsolePurchaseSummaryPrinter.class.getName());
        logger.addHandler(handler);
    }

    @AfterEach
    void removeLogHandler() {
        logger.removeHandler(handler);
    }

    @Test
    void printShouldLogCorrectSummaryWhenNoDiscounts() {
        //Given
        var purchaseSummary = PurchaseSummary.builder()
                .subTotal(BigDecimal.valueOf(3.1))
                .discounts(List.of())
                .total(BigDecimal.valueOf(3.1))
                .build();
        //When
        new ConsolePurchaseSummaryPrinter().print(purchaseSummary);
        //Then
        handler.flush();
        assertTrue(logs.toString().contains("Subtotal: £3.1"));
        assertTrue(logs.toString().contains("(No offers available)"));
        assertTrue(logs.toString().contains("Total: £3.1"));
    }

    @Test
    void printShouldLogCorrectSummaryWithDiscounts() {
        // Given
        var appleTenPercentDiscount = Discount.builder()
                .label("Apples 10% off")
                .amount(BigDecimal.valueOf(0.1))
                .build();
        var halfBreadForTwoSoupDiscount = Discount.builder()
                .label("2 tins of soup and get a loaf of bread")
                .amount(BigDecimal.valueOf(0.4))
                .build();
        var discounts = List.of(appleTenPercentDiscount, halfBreadForTwoSoupDiscount);
        var purchaseSummary = PurchaseSummary.builder()
                .subTotal(BigDecimal.valueOf(3.1))
                .discounts(discounts)
                .total(BigDecimal.valueOf(3.0))
                .build();
        //When
        new ConsolePurchaseSummaryPrinter().print(purchaseSummary);
        //Then
        handler.flush();
        assertTrue(logs.toString().contains("Subtotal: £3.1"));
        assertTrue(logs.toString().contains("Apples 10% off: -0.1£"));
        assertTrue(logs.toString().contains("2 tins of soup and get a loaf of bread: -0.4£"));
        assertTrue(logs.toString().contains("Total: £3.0"));
    }

}