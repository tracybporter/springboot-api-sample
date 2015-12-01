package com.phg.productsapi.blackbox

import com.phg.productsapi.MainApplication
import groovyx.net.http.RESTClient
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = MainApplication.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@IntegrationTest
class RootFnSpec extends Specification {
   def "default root response from lazybones sample"() {
      given:
      RESTClient restClient = new RESTClient('http://localhost:8080')

      when:
      def response = restClient.get(path: '/')

      then:
      response.status == 200
      response.headers.'Content-Type'.toString() == 'application/json;charset=UTF-8'
      response.data.message == 'Hello World'

   }
}
