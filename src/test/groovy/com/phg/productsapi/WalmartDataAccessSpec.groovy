package com.phg.productsapi

import com.phg.productsapi.domain.Product
import com.phg.productsapi.service.WalmartDataAccess
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import org.apache.http.client.HttpResponseException
import org.slf4j.Logger
import spock.lang.Shared
import spock.lang.Specification

class WalmartDataAccessSpec extends Specification {
    @Shared
    WalmartDataAccess dataAccess

    @Shared
    Logger mockLogger

    def setup() {
        mockLogger = Mock Logger
        dataAccess = new WalmartDataAccess()
        dataAccess.logger = mockLogger
        dataAccess.apiKey = 'anything'
        dataAccess.dataHost = 'http://anything.com'
    }

    def 'queries walmart with upc and api key as query parameters'() {
        given:
        Product mockProduct = GroovyMock(Product, global: true)
        RESTClient mockRestClient = GroovyMock(RESTClient, global: true)
        long upc = 12
        HttpResponseDecorator mockResponse = Mock HttpResponseDecorator
        Map responseData = [anything: 'not the responsibility here']

        when:
        Product actual = dataAccess.queryByUpc(upc)

        then:
        1 * new RESTClient('http://anything.com') >> mockRestClient
        1 * mockRestClient.get([path: '/v1/items', query: [upc: upc, apiKey: 'anything']]) >> mockResponse
        1 * mockResponse.getData() >> responseData
        1 * new Product(responseData) >> mockProduct
        actual == mockProduct
    }

    def 'adds meaningful message when Walmart responds with Bad Request'() {
        given:
        RESTClient mockRestClient = GroovyMock(RESTClient, global: true)
        new RESTClient(_) >> mockRestClient
        mockRestClient.get(_) >> { throw new HttpResponseException(400, 'Bad Request') }

        when:
        dataAccess.queryByUpc(33)

        then:
        1 * mockLogger.error('For UPC=33, Problem=Bad Request')
        def e = thrown(DataAccessException)
        e.message == 'For UPC=33, Problem=Bad Request'
        e.statusCode == 400
        e.dataAccessSource == 'http://anything.com'
    }

    def 'adds meaningful message when unexpected error'() {
        given:
        RESTClient mockRestClient = GroovyMock(RESTClient, global: true)
        new RESTClient(_) >> mockRestClient
        mockRestClient.get(_) >> { throw new IOException('not good', new RuntimeException('another message')) }

        when:
        dataAccess.queryByUpc(33)

        then:
        1 * mockLogger.error(*_) >> { args ->
            assert args[0].startsWith('For UPC=33, Problem=not good')
            assert args[0].contains('another message')
        }
        def e = thrown(DataAccessException)
        e.message.startsWith('For UPC=33, Problem=not good')
        e.message.contains('another message')
        e.statusCode == 500
        e.dataAccessSource == 'http://anything.com'
    }
}
