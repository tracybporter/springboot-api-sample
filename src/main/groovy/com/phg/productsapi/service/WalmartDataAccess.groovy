package com.phg.productsapi.service

import com.phg.productsapi.DataAccessException
import com.phg.productsapi.domain.Product
import groovyx.net.http.RESTClient
import org.apache.http.client.HttpResponseException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class WalmartDataAccess {
    Logger logger = LoggerFactory.getLogger(WalmartDataAccess)

    @Value('${walmartlabs.apikey}')
    String apiKey

    @Value('${walmartlabs.host}')
    String dataHost

    Product queryByUpc(long upc) {
        def result
        RESTClient restClient = new RESTClient(dataHost)
        try {
            Map queryParameters = [upc: upc, apiKey: apiKey]
            def response = restClient.get([path: '/v1/items', query: queryParameters])
            return new Product(response.data)
        } catch (HttpResponseException httpException) {
            String message = "For UPC=${upc}, Problem=${httpException.localizedMessage}"
            logger.error(message)
            throw new DataAccessException(
                    statusCode: httpException.statusCode,
                    message: message,
                    dataAccessSource: dataHost)
        } catch (Exception ex) {
            String message = "For UPC=${upc}, Problem=${ex.message}, Cause=${ex.cause}"
            logger.error(message)
            throw new DataAccessException(
                    statusCode: 500,
                    message: message,
                    dataAccessSource: dataHost)
        }
    }
}