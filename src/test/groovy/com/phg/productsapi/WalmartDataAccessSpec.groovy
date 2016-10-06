package com.phg.productsapi

import com.phg.productsapi.domain.Product
import com.phg.productsapi.service.WalmartDataAccess
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import org.apache.http.client.HttpResponseException
import spock.lang.Shared
import spock.lang.Specification

class WalmartDataAccessSpec extends Specification {
    @Shared
    WalmartDataAccess dataAccess

    def setup() {
        dataAccess = new WalmartDataAccess()
        dataAccess.apiKey = 'anything'
    }

    def 'queries walmart with upc and api key as query parameters'() {
        given:
        RESTClient mockRestClient = GroovyMock(RESTClient, global: true)
        Product mockProduct = GroovyMock(Product, global: true)
        long upc = 12
        HttpResponseDecorator mockResponse = Mock HttpResponseDecorator
        Map responseData = [anything: 'not the responsibility here']

        when:
        Product actual = dataAccess.queryByUpc(upc)

        then:
        1 * new RESTClient('http://api.walmartlabs.com') >> mockRestClient
        1 * mockRestClient.get([path: '/v1/items', query: [upc: upc, apiKey: 'anything']]) >> mockResponse
        1 * mockResponse.getData() >> responseData
        1 * new Product(responseData) >> mockProduct
        actual == mockProduct
    }

    def 'adds meaningful message when Walmart responds with Bad Request'() {
        given:
        RESTClient mockRestClient = GroovyMock(RESTClient, global: true)

        when:
        dataAccess.queryByUpc(33)

        then:
        new RESTClient(_) >> mockRestClient
        mockRestClient.get(_) >> { throw new HttpResponseException(400, 'Bad Request') }
        def e = thrown(DataAccessException)
        e.message == 'For UPC=33, Problem=Bad Request'
        e.statusCode == 400
        e.dataAccessSource == 'http://api.walmartlabs.com'
    }

    def 'adds meaningful message when unexpected error'() {
        given:
        RESTClient mockRestClient = GroovyMock(RESTClient, global: true)

        when:
        dataAccess.queryByUpc(33)

        then:
        new RESTClient(_) >> mockRestClient
        mockRestClient.get(_) >> { throw new IOException('not good', new RuntimeException('another message')) }
        def e = thrown(DataAccessException)
        e.message.startsWith('For UPC=33')
        e.message.contains('not good')
        e.message.contains('another message')
        e.statusCode == 500
        e.dataAccessSource == 'http://api.walmartlabs.com'
    }
}
