package dev.unify.service;

import dev.unify.model.Basket;
import dev.unify.model.PurchaseSummary;

import java.util.List;

public interface PurchaseSummaryService {

    PurchaseSummary getPurchaseSummary(final Basket basket, final List<Offer> offers);
}
