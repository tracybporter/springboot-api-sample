package com.phg.productsapi.service

import com.phg.productsapi.domain.PageResult
import com.phg.productsapi.domain.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductsService {
    @Autowired
    WalmartDataAccess walmartDataAccess

    PageResult findByUpc(long upc) {
        Product product = walmartDataAccess.queryByUpc(upc)
        new PageResult([product])
    }

}
