package dev.unify.model;

import java.util.Map;

public class Basket {

    private final Map<Item, Integer> content;

    public Basket(final Map<Item, Integer> content) {
        this.content = content;
    }

    public Map<Item, Integer> getContent() {
        return Map.copyOf(this.content);
    }
}
