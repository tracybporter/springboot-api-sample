package com.phg.productsapi

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource('classpath:/appversion.properties')
class MainApplication {

    static void main(String[] args) throws Exception {
        SpringApplication.run(MainApplication.class, args)
    }

}
