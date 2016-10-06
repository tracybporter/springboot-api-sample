package com.phg.productsapi

import com.phg.productsapi.domain.Descriptions
import com.phg.productsapi.domain.PageResult
import com.phg.productsapi.domain.Prices
import com.phg.productsapi.domain.Product
import com.phg.productsapi.service.ProductsService
import com.phg.productsapi.service.WalmartDataAccess
import spock.lang.Shared
import spock.lang.Specification

class ProductsServiceSpec extends Specification {
    @Shared
    ProductsService productsService

    def setup() {
        productsService = new ProductsService()
    }

    def 'delegate to WalmartDataAccess when searching by UPC'() {
        given:
        WalmartDataAccess mockWalmartAccess = Mock WalmartDataAccess
        productsService.walmartDataAccess = mockWalmartAccess
        Product product = new Product()
        product.descriptions = new Descriptions(display: 'Product Name')
        product.prices = new Prices(msrp: new BigDecimal('3.33'))

        when:
        PageResult actual = productsService.findByUpc(93)

        then:
        1 * mockWalmartAccess.queryByUpc(93) >> product
        actual.results.size() == 1
        actual.results[0].descriptions.display == 'Product Name'
        actual.results[0].prices.msrp == new BigDecimal('3.33')
    }
}