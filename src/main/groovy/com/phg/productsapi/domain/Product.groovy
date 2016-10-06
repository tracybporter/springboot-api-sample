package com.phg.productsapi.domain

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode()
class Product {
    String upc
    Descriptions descriptions
    Prices prices = new Prices()

    Product() {}

    Product(def walmartData) {
        if (!walmartData.isEmpty() && walmartData.items) {
            def firstItem = walmartData.items[0]
            upc = firstItem.upc
            descriptions = new Descriptions(display: firstItem.name)

            String msrp = firstItem.msrp
            if (msrp) {
                prices.msrp = new BigDecimal(msrp)
            }
        } else {
            throw new IllegalArgumentException("Not able to process response: ${walmartData}")
        }
    }
}

@EqualsAndHashCode()
class Descriptions {
    String display
}

@EqualsAndHashCode()
class Prices {
    BigDecimal msrp
}