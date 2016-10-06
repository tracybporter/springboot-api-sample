package com.phg.productsapi

import com.phg.productsapi.service.WalmartDataAccess
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification

class WalmartDataAccessIntegrationSpec extends Specification {
    @Shared
    WalmartDataAccess dataAccess

    def setup() {
        dataAccess = new WalmartDataAccess()
        dataAccess.apiKey = 'anything'
    }

    def 'Adds meaningful message when Walmart responds cannot be parsed'() {
        //Integration test because I was unable to create 2 GroovyMocks in the same test method.
        given:
        RESTClient mockRestClient = GroovyMock(RESTClient, global: true)
        HttpResponseDecorator mockResponse = Mock HttpResponseDecorator

        when:
        dataAccess.queryByUpc(7777)

        then:
        new RESTClient(_) >> mockRestClient
        mockRestClient.get(_) >> mockResponse
        mockResponse.getData() >> ['doesNot': 'matter']
        def e = thrown(DataAccessException)
        e.message == 'For UPC=7777, Problem=Not able to process response: [doesNot:matter]'
        e.statusCode == 500
        e.dataAccessSource == 'http://api.walmartlabs.com'
    }
}
