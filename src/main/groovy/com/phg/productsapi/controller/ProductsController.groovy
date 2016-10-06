package com.phg.productsapi.controller

import com.phg.productsapi.DataAccessException
import com.phg.productsapi.domain.PageResult
import com.phg.productsapi.service.ProductsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(value = "/v1/products")
class ProductsController {
    ProductsService service

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.service = productsService
    }

    @RequestMapping(method = RequestMethod.GET, params = "upc")
    public PageResult find(@RequestParam("upc") long upcId) {
        service.findByUpc(upcId)
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(DataAccessException)
    DataAccessException dataAccessIssue(DataAccessException dataAccessException, HttpServletResponse response) {
        response.sendError(dataAccessException.statusCode, dataAccessException.message)
    }
}
