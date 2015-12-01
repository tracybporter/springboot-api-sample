
package com.phg.productsapi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Configuration
@ComponentScan
@EnableAutoConfiguration
@RestController
class MainApplication {

   @RequestMapping("/")
   def helloWorld() {
      [message: "Hello World"]
   }

   static void main(String[] args) throws Exception {
      SpringApplication.run(MainApplication.class, args)
   }

}
