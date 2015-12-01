package com.phg.productsapi.unit

import com.phg.productsapi.service.SemanticsDataAccess
import com.phg.productsapi.thirdparty.SemanticProduct
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import spock.lang.Specification

class SemanticsDataAccessSpec extends Specification {
   def 'queries semantic test environment with strange encoded query parameters and api key in header'() {
      given:
      System.properties.setProperty 'semantics3.apikey', 'a11111'
      SemanticsDataAccess dataAccess = new SemanticsDataAccess()
      RESTClient mockRestClient = Mock RESTClient
      dataAccess.restClient = mockRestClient

      HttpResponseDecorator mockResponse = Mock HttpResponseDecorator

      long upc = 999999999999
      URI uri = new URI(scheme: 'https', host: 'api.semantics3.com', path: '/test/v1/products')
      uri.query = URLEncoder.encode('q={"upc":"' + upc + '","fields":["name","price"]}', "UTF-8")

      when:
      SemanticProduct actual = dataAccess.queryByUpc(upc)

      then:
      1 * mockRestClient.setHeaders([api_key: 'a11111'])
      1 * mockRestClient.get(["uri": uri]) >> mockResponse
      1 * mockResponse.getData() >> [results: [[name: 'any name', price: '3.31'], [name: 'ignored', price: '2.01']]]
      actual.name == 'any name'
      actual.price == new BigDecimal('3.31')
   }
}
