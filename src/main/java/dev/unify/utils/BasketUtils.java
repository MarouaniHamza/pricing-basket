package dev.unify.utils;

import dev.unify.model.Basket;
import dev.unify.model.Item;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class BasketUtils {

    public static Basket fromList(final List<String> input) {
        if (input == null) {
            return new Basket(Collections.emptyMap());
        }
        final var content = input.stream()
                .map(Item::fromLabel)
                .collect(
                        Collectors.toMap(
                                Function.identity(),
                                value -> 1,
                                Integer::sum
                        )
                );
        return new Basket(content);
    }

    public static int getItemCount(final Item item, final Basket basket) {
        if (item == null) {
            return 0;
        }
        return Optional.ofNullable(basket)
                .map(Basket::getContent)
                .map(content -> content.get(item))
                .orElse(0);
    }
}
