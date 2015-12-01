package com.phg.productsapi.service

import com.phg.productsapi.thirdparty.SemanticProduct
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import org.springframework.stereotype.Component

@Component
class SemanticsDataAccess {
   RESTClient restClient
   URI uri
   String apiKey

   public SemanticsDataAccess() {
      apiKey = System.properties.'semantics3.apikey'
      restClient = new RESTClient()
      uri = new URI(scheme: 'https', host: 'api.semantics3.com', path: '/test/v1/products')
   }

   SemanticProduct queryByUpc(long upc) {
      println "************ api key value:${apiKey}"
      restClient.headers = [api_key: apiKey]

      uri.query = URLEncoder.encode('q={"upc":"' + upc + '","fields":["name","price"]}', "UTF-8")
      HttpResponseDecorator response = restClient.get(["uri": uri])
      Map result = response.data.results[0]
      return new SemanticProduct(name: result.name, price: new BigDecimal(result.price))
   }
}
