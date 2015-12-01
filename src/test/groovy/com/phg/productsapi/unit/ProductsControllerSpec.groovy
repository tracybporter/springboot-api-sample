package com.phg.productsapi.unit

import com.phg.productsapi.controller.ProductsController
import com.phg.productsapi.domain.PageResult
import com.phg.productsapi.service.ProductsService
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class ProductsControllerSpec extends Specification {
   ProductsController productsController
   ProductsService mockProductsService
   MockMvc mockMvc

   def setup() {
      productsController = new ProductsController()
      mockProductsService = Mock(ProductsService.class)
      productsController.service = mockProductsService;
      mockMvc = standaloneSetup(productsController).build()
   }

   def 'products controller delegates to the service for the list of products'() {
      when:
      def response = mockMvc.perform(get("/v1/products")).andReturn().response

      then:
      1 * mockProductsService.retrieveAll() >> new PageResult()
      response.status == OK.value()
   }

   def 'products controller delegates to the service for query by UPC value'() {
      when:
      def response = mockMvc.perform(get("/v1/products?upc=111")).andReturn().response

      then:
      1 * mockProductsService.findByUpc(111) >> new PageResult()
      response.status == OK.value()
   }
}
