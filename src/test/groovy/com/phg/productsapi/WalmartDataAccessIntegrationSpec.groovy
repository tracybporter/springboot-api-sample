package com.phg.productsapi

import com.phg.productsapi.service.WalmartDataAccess
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import org.slf4j.Logger
import spock.lang.Shared
import spock.lang.Specification

class WalmartDataAccessIntegrationSpec extends Specification {
    @Shared
    WalmartDataAccess dataAccess

    @Shared
    Logger mockLogger

    def setup() {
        mockLogger = Mock Logger
        dataAccess = new WalmartDataAccess()
        dataAccess.logger = mockLogger
        dataAccess.apiKey = 'anything'
        dataAccess.dataHost = 'else'
    }

    def 'Adds meaningful message when Walmart responds cannot be parsed'() {
        //Integration test because I was unable to create 2 GroovyMocks in the same test method.
        given:
        HttpResponseDecorator mockResponse = Mock HttpResponseDecorator
        RESTClient mockRestClient = GroovyMock(RESTClient, global: true)
        new RESTClient(_) >> mockRestClient
        mockRestClient.get(_) >> mockResponse

        when:
        dataAccess.queryByUpc(7777)

        then:
        mockResponse.getData() >> ['doesNot': 'matter']
        1 * mockLogger.error('For UPC=7777, Problem=Not able to process response: [doesNot:matter], Cause=null')
        def e = thrown(DataAccessException)
        e.message == 'For UPC=7777, Problem=Not able to process response: [doesNot:matter], Cause=null'
        e.statusCode == 500
        e.dataAccessSource == 'else'
    }
}
