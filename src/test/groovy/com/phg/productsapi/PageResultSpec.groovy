package com.phg.productsapi

import com.phg.productsapi.domain.PageResult
import spock.lang.Specification

class PageResultSpec extends Specification {
   def 'default constructor sets count to zero'() {
      when:
      PageResult pageResult = new PageResult()

      then:
      pageResult.metadata.resultSet.count == 0
      pageResult.metadata.resultSet.offset == 0
      pageResult.metadata.resultSet.limit == 20
      pageResult.results.size() == 0
   }

   def 'constructor takes list of products and sets metadata.resultSet.count'() {
      when:
      PageResult pageResult = new PageResult(['Testing', 'Simple', 'Constructor'])

      then:
      pageResult.metadata.resultSet.count == 3
   }
}
