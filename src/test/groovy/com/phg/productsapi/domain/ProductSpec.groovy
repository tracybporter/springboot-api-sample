package com.phg.productsapi.domain

import spock.lang.Specification
import spock.lang.Unroll

class ProductSpec extends Specification {

    def 'constructs display description from WalmartProduct item name'() {
        given:
        Map walmartRespose = [items: [
                [name: 'the product name'],
                [name: 'not used']
        ]]

        expect:
        new Product(walmartRespose).descriptions.display == 'the product name'
    }

    def 'constructs pricefrom WalmartProduct item salePrice'() {
        expect:
        new Product([items: [[msrp: 23.86]]]).prices.msrp == new BigDecimal('23.86')
    }

    def 'constructs upc WalmartProduct item upc'() {
        expect:
        new Product([items: [[upc: 'upc-1']]]).upc == 'upc-1'
    }

    @Unroll
    def 'throw IllegalArgumentException when Walmart responds cannot be parsed for scenario #scenario'() {
        when:
        new Product(walmartData)

        then:
        def e = thrown(IllegalArgumentException)
        e.message == 'Not able to process response: ' + walmartData

        where:
        scenario                      | walmartData
        'root attribute is different' | [not_items: [[name: 'any name', msrp: 3.31]]]
        'empty'                       | []
    }
}
