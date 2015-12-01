package com.phg.productsapi.unit

import com.phg.productsapi.domain.Descriptions
import com.phg.productsapi.domain.PageResult
import com.phg.productsapi.domain.Product
import com.phg.productsapi.domain.RelatedLinks
import com.phg.productsapi.service.ProductsService
import com.phg.productsapi.service.SemanticsDataAccess
import com.phg.productsapi.thirdparty.SemanticProduct
import spock.lang.Shared
import spock.lang.Specification

class ProductsServiceSpec extends Specification {
   @Shared
   ProductsService productsService

   def setup() {
      productsService = new ProductsService()
   }

   def 'provides a static response for retrieve all'() {
      given:
      Descriptions description = new Descriptions(display: 'should also include short and long, but those are reserve words and am not worrying about that now.')
      Product product1 = new Product(id: 'id-1', upc: 'upc-1', descriptions: description, links: new RelatedLinks(self: 'http://localhost:8080/v1/products/id-1'))
      Product product2 = new Product(id: 'id-2', upc: 'upc-2', descriptions: description, links: new RelatedLinks(self: 'http://localhost:8080/v1/products/id-2'))
      Product product3 = new Product(id: 'id-3', upc: 'upc-3', descriptions: description, links: new RelatedLinks(self: 'http://localhost:8080/v1/products/id-3'))

      PageResult expected = new PageResult([product1, product2, product3])

      when:
      PageResult actual = productsService.retrieveAll()

      then:
      actual.results.size() == expected.results.size()
      actual == expected
   }

   def 'delegate to SemanticProductDataAccess when searching by UPC'() {
      given:
      SemanticsDataAccess mockSemanticsDataAccess = Mock(SemanticsDataAccess)
      productsService.semanticsDataAccess = mockSemanticsDataAccess

      when:
      PageResult actual = productsService.findByUpc(93)

      then:
      1 * mockSemanticsDataAccess.queryByUpc(93) >> new SemanticProduct(name: 'Product Name', price: new BigDecimal('3.33'))
      actual.results.size() == 1
      actual.results[0].descriptions.display == 'Product Name'
      actual.results[0].prices.msrp == new BigDecimal('3.33')
   }
}