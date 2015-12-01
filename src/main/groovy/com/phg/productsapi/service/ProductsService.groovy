package com.phg.productsapi.service

import com.phg.productsapi.domain.Descriptions
import com.phg.productsapi.domain.PageResult
import com.phg.productsapi.domain.Prices
import com.phg.productsapi.domain.Product
import com.phg.productsapi.domain.RelatedLinks
import com.phg.productsapi.thirdparty.SemanticProduct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class ProductsService {
   @Autowired
   SemanticsDataAccess semanticsDataAccess

   PageResult retrieveAll() {
      new PageResult([constructProduct('id-1', 'upc-1'),
                      constructProduct('id-2', 'upc-2'),
                      constructProduct('id-3', 'upc-3')])
   }

   PageResult findByUpc(long upc) {
      SemanticProduct semanticProduct = semanticsDataAccess.queryByUpc(upc)
      String productId = 'unknown'
      new PageResult([new Product(id: productId, upc: String.valueOf(upc), descriptions: new Descriptions(display: semanticProduct.name), links: manufactureSelfLink(productId), prices: new Prices(msrp: semanticProduct.price))])
   }

   private Product constructProduct(String productId, String upc) {
      String display = 'should also include short and long, but those are reserve words and am not worrying about that now.'
      Descriptions description = new Descriptions(display: display)
      return new Product(id: productId, upc: upc, descriptions: description, links: manufactureSelfLink(productId))
   }

   private RelatedLinks manufactureSelfLink(String productId) {
      new RelatedLinks(self: "http://localhost:8080/v1/products/${productId}")
   }
}
