package dev.unify.service;

import dev.unify.model.Basket;
import dev.unify.model.Discount;

public interface Offer {

    Discount apply(final Basket basket);
}
