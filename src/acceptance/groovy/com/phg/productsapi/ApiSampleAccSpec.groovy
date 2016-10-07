package com.phg.productsapi

import groovyx.net.http.RESTClient
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = MainApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
class ApiSampleAccSpec extends Specification {

    def "/info returns application version"() {
        given:
        RESTClient restClient = new RESTClient('http://localhost:8080')

        when:
        def response = restClient.get(path: '/info')
        def data = response.data

        then:
        response.status == 200
        response.headers.'Content-Type'.toString() == 'application/json;charset=UTF-8'
        data.app.version == '0.1.1'
    }
}
