# pricing-basket

The purpose of this program is to give the price of a basket of goods taking into account some special offers.

## Features

Given a command line input of a list of items, this program outputs the subtotal, the applied offers and the total.
example:
### input:
```bash
java -jar pricing-basket.jar Apples Milk Bread
### output:
Subtotal: £3.10
Apples 10% off: -10p
Total: £3.00
````

## Prerequisites

- Java 21 (or higher)
- Maven

## Installation

run the command
```bash
mvn clean install
```

take the jar file under the `target` directory.

## Design Choices

We choose to decompose the model into the following entities

| Class Name      | Description                                                                                                                                   |
|-----------------|-----------------------------------------------------------------------------------------------------------------------------------------------|
| Item            | A given purchased item (example Apple, Bread) with its code and price                                                                         |
| Basket          | Represents the purchased items and their count (example [<Apple, 3>, <Bread, 2>])                                                             |
| Discount        | The result of applying an offer on a given item, it is represented by the offer's label and the obtained discount                             |
| PurchaseSummary | An object representing the result of a purchased basket, it contains the subtotal, the total after applying the offers and the applied offers |

For business logic implementation, we have the following classes

| Class Name               | Description                                                                                                                                                                             |
|--------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Offer                    | An interface representing a strategy for computing and applying discount                                                                                                                |
| SimpleDiscountOffer      | An implementation of the interface `Offer` where we have a discount computed on an item based on a percentage (example 10% discount on Apples)                                          |
| ConditionalDiscountOffer | An implementation of the interface `Offer` where a discount is computed based on a bought item and applied on another item in the basket (example 50% on Bread for each 2 tins of Soup) |
| PurchaseSummaryService   | The actual service that computes the purchase summary given a basket and a list of offers                                                                                               |
| PurchaseSummaryPrinter   | The interface used to output the content of the `PurchaseSummary`                                                                                                                       |
| BasketUtils              | A utility class dedicated to operations on basket objects                                                                                                                               |

For the implementation of the `PurchaseSummaryService` (mainly the class `PurchaseSummaryServiceImpl`) we use the following steps:
1. Instantiate a `Basket` from the user's input
2. Compute the subtotal from the basket (total before applying the offers)
3. For each offer in the input, look for an item that fits into its strategy and return a `Discount`
4. Compute the total of the discounts and subtract from the subtotal to get the total
5. Return the obtained information (subtotal, list of discounts and total) as a `PurchaseSummary` object
