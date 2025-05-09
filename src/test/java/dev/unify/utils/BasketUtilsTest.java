package dev.unify.utils;

import dev.unify.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

class BasketUtilsTest {

    private static final String APPLES = "Apples";
    private static final String BREAD = "Bread";
    private static final String SOUP = "Soup";

    @Test
    void getContentShouldReturnEmptyMapWhenInputIsNull() {
        //Given
        var basket = BasketUtils.fromList(null);
        //When
        var actualContent = basket.getContent();
        //Then
        Assertions.assertEquals(Collections.emptyMap(), actualContent);
    }

    @ParameterizedTest
    @MethodSource("provideTestCasesForBasket")
    void getContentShouldReturnMapWhenInputIsNotNullOrEmpty(List<String> input, Map<Item, Integer> expectedContent) {
        //Given
        var basket = BasketUtils.fromList(input);
        //When
        var actualContent = basket.getContent();
        //Then
        Assertions.assertEquals(expectedContent, actualContent);
    }

    @ParameterizedTest
    @MethodSource("provideTestCasesForBasket")
    void getItemCountShouldReturn0MapWhenItemIsNotFound(List<String> input) {
        //Given
        var basket = BasketUtils.fromList(input);
        //When
        var itemContent = BasketUtils.getItemCount(Item.MILK, basket);
        //Then
        Assertions.assertEquals(0, itemContent);
    }

    @ParameterizedTest
    @MethodSource("provideTestCasesForBasket")
    void getItemCountShouldReturn0MapWhenItemIsNull(List<String> input) {
        //Given
        var basket = BasketUtils.fromList(input);
        //When
        var itemContent = BasketUtils.getItemCount(null, basket);
        //Then
        Assertions.assertEquals(0, itemContent);
    }

    @ParameterizedTest
    @MethodSource("provideTestCasesForBasket")
    void getItemCountShouldReturnNumberOfItemsWhenInputIsNotNullOrEmpty(List<String> input) {
        //Given
        var basket = BasketUtils.fromList(input);
        //When
        var itemContent = BasketUtils.getItemCount(Item.APPLES, basket);
        //Then
        Assertions.assertEquals(1, itemContent);
    }

    private static Stream<Arguments> provideTestCasesForBasket() {
        var onlyApples = List.of(APPLES);
        Map<Item, Integer> expectedContentOnlyApples = Map.of(Item.APPLES, 1);
        var apples2Bread = List.of(APPLES, BREAD, BREAD);
        Map<Item, Integer> expectedContentApples2Bread = Map.of(Item.APPLES, 1, Item.BREAD, 2);
        var apples2Bread3Soup = List.of(APPLES, BREAD, BREAD, SOUP, SOUP, SOUP);
        Map<Item, Integer> expectedContentApples2Bread3Soup = Map.of(Item.APPLES, 1, Item.BREAD, 2, Item.SOUP, 3);
        return Stream.of(
                Arguments.of(onlyApples, expectedContentOnlyApples),
                Arguments.of(apples2Bread, expectedContentApples2Bread),
                Arguments.of(apples2Bread3Soup, expectedContentApples2Bread3Soup)
        );
    }
}
