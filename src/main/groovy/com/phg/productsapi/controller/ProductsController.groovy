package com.phg.productsapi.controller

import com.phg.productsapi.domain.PageResult
import com.phg.productsapi.service.ProductsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping(value = "/v1/products")
class ProductsController {
   ProductsService service

   @Autowired
   public ProductsController(ProductsService productsService) {
      this.service = productsService
   }

   @RequestMapping(method = RequestMethod.GET)
   public PageResult getProducts() {
      service.retrieveAll()
   }

   @RequestMapping(method = RequestMethod.GET, params = "upc")
   public PageResult find(@RequestParam("upc") long upcId) {
      service.findByUpc(upcId)
   }
}
