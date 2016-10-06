package com.phg.productsapi.service

import com.phg.productsapi.DataAccessException
import com.phg.productsapi.domain.Product
import groovyx.net.http.RESTClient
import org.apache.http.client.HttpResponseException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class WalmartDataAccess {

    @Value('${walmartlabs.apikey}')
    String apiKey

    String dataHost = 'http://api.walmartlabs.com'

    Product queryByUpc(long upc) {
        def result
        RESTClient restClient = new RESTClient(dataHost)
        try {
            Map queryParameters = [upc: upc, apiKey: apiKey]
            def response = restClient.get([path: '/v1/items', query: queryParameters])
            return new Product(response.data)
        } catch (HttpResponseException httpException) {
            throw new DataAccessException(
                    statusCode: httpException.statusCode,
                    message: "For UPC=${upc}, Problem=${httpException.localizedMessage}",
                    dataAccessSource: dataHost)
        } catch (IllegalArgumentException illegalArgument) {
            throw new DataAccessException(
                    statusCode: 500,
                    message: "For UPC=${upc}, Problem=${illegalArgument.message}",
                    dataAccessSource: dataHost)
        } catch (Exception ex) {
            throw new DataAccessException(
                    statusCode: 500,
                    message: "For UPC=${upc}, Problem=${ex.message}\n${ex.cause}",
                    dataAccessSource: dataHost)
        }
    }
}