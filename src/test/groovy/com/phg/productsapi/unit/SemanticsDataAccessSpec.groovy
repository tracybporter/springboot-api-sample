package com.phg.productsapi.unit

import com.phg.productsapi.DataAccessException
import com.phg.productsapi.service.SemanticsDataAccess
import com.phg.productsapi.thirdparty.SemanticProduct
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import org.apache.http.client.HttpResponseException
import spock.lang.Shared
import spock.lang.Specification

class SemanticsDataAccessSpec extends Specification {
   @Shared
   SemanticsDataAccess dataAccess
   @Shared
   RESTClient mockRestClient

   def setup() {
      dataAccess = new SemanticsDataAccess()
      mockRestClient = Mock RESTClient
      dataAccess.restClient = mockRestClient
   }

   def 'queries semantic test environment with strange encoded query parameters and api key in header'() {
      given:
      dataAccess.apiKey = 'a11111'
      long upc = 999999999999
      URI uri = new URI(scheme: 'https', host: 'api.semantics3.com', path: '/test/v1/products')
      uri.query = URLEncoder.encode('q={"upc":"' + upc + '","fields":["name","price"]}', "UTF-8")
      HttpResponseDecorator mockResponse = Mock HttpResponseDecorator

      when:
      SemanticProduct actual = dataAccess.queryByUpc(upc)

      then:
      1 * mockRestClient.setHeaders([api_key: 'a11111'])
      1 * mockRestClient.get(["uri": uri]) >> mockResponse
      1 * mockResponse.getData() >> [results: [[name: 'any name', price: '3.31'], [name: 'ignored', price: '2.01']]]
      actual.name == 'any name'
      actual.price == new BigDecimal('3.31')
   }

   def 'Adds meaningful message when Semantics responds with Bad Request'() {
      given:
      HttpResponseDecorator response = Mock HttpResponseDecorator
      response.statusCode >> 400
      response.reasonPhrase >> 'Bad Request'

      when:
      dataAccess.queryByUpc(7777)

      then:
      mockRestClient.get(_) >> { throw new HttpResponseException(400, 'Bad Request') }
      def e = thrown(DataAccessException)
      e.message == 'Bad Request for UPC: 7777'
      e.statusCode == 400
      e.dataAccessSource == 'api.semantics3.com'
   }

}
