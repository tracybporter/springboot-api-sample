package com.phg.productsapi.domain

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includeFields = true)
class Product {
   String id
   String upc
   Descriptions descriptions
   RelatedLinks links
   Prices prices = new Prices()
}

@EqualsAndHashCode(includeFields = true)
class Descriptions {
   String display
}

@EqualsAndHashCode(includeFields = true)
class RelatedLinks {
   String self
}

@EqualsAndHashCode(includeFields = true)
class Prices {
   BigDecimal msrp
}