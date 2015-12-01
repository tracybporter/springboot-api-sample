package com.phg.productsapi.blackbox

import com.phg.productsapi.MainApplication
import groovyx.net.http.RESTClient
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Shared
import spock.lang.Specification

@ContextConfiguration(classes = MainApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
class ProductsFnSpec extends Specification {
   @Shared
   RESTClient restClient

   def setup() {
      restClient = new RESTClient('http://localhost:8080')
   }

   def '/products returns a list of products'() {
      when:
      def response = restClient.get(path: '/v1/products')

      then:
      response.status == 200
      response.headers.'Content-Type'.toString() == 'application/json;charset=UTF-8'
      response.data.metadata.resultSet.count == 3
      response.data.metadata.resultSet.offset == 0
      response.data.metadata.resultSet.limit == 20
      response.data.results.size() == 3
      response.data.results[0].id
      response.data.results[0].upc
      response.data.results[0].descriptions.display
      response.data.results[0].links.self.startsWith 'http://localhost:8080/v1/products/'
   }

   def 'find products by UPC returns list of products'() {
      when:
      def response = restClient.get(path: "/v1/products", query: [upc: 786936175271])

      then:
      response.status == 200
      response.headers.'Content-Type'.toString() == 'application/json;charset=UTF-8'
      response.data.metadata.resultSet.count == 1
      response.data.metadata.resultSet.offset == 0
      response.data.metadata.resultSet.limit == 20
      response.data.results.size() == 1
      response.data.results[0].id
      response.data.results[0].upc == '786936175271'
      response.data.results[0].descriptions.display == 'My Neighbor Totoro'
      response.data.results[0].links.self.startsWith 'http://localhost:8080/v1/products/'
      response.data.results[0].prices.msrp >= new BigDecimal('9.99')
   }

   def 'bad UPC results in a bad request reponse'() {
      given:
      restClient.handler.failure = { resp, data ->
         println '*********** data: ' + data
         resp.setData(data)
         String headers = ""
         resp.headers.each {
            headers = headers + "${it.name} : ${it.value}\n"
         }
         return resp
      }
      when:
      def response = restClient.get(path: "/v1/products", query: [upc: 33])

      then:
      response.status == 400
      response.headers.'Content-Type'.toString() == 'application/json;charset=UTF-8'
      response.status == 400
      response.error == 'Bad Request'
      response.message == 'Bad Request for UPC: 33'
      response.timestamp
      response.exception
   }
}
