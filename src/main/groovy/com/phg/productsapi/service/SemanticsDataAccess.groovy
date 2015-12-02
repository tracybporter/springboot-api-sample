package com.phg.productsapi.service

import com.phg.productsapi.DataAccessException
import com.phg.productsapi.thirdparty.SemanticProduct
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import org.apache.http.client.HttpResponseException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SemanticsDataAccess {
   @Value('${semantics3.apikey}')
   public String apiKey

   RESTClient restClient
   URI uri
   String dataHost = 'api.semantics3.com'

   public SemanticsDataAccess() {
      restClient = new RESTClient()
      uri = new URI(scheme: 'https', host: dataHost, path: '/test/v1/products')
   }

   SemanticProduct queryByUpc(long upc) {
      restClient.headers = [api_key: apiKey]
      uri.query = URLEncoder.encode('q={"upc":"' + upc + '","fields":["name","price"]}', "UTF-8")
      try {
         HttpResponseDecorator response = restClient.get(["uri": uri])
         Map result = response.data.results[0]
         return new SemanticProduct(name: result.name, price: new BigDecimal(result.price))
      } catch (HttpResponseException httpException) {
         throw new DataAccessException(
               statusCode: httpException.statusCode,
               message: httpException.localizedMessage + " for UPC: ${upc}",
               dataAccessSource: dataHost)
      }
   }
}
