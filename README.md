# Overview
This repository evolves sporatically as I get time between contracts. I started it in 2015 using a lazybones template. It is a simple test-first RESTful API.


# To Run
1. Clone
1. Build and test without aquiring a walmartlabs api key.
    1. terminal: `./gradlew -Dspring.profiles.active=stubbydev -Dwalmartlabs.apikey=2BorNot2B clean build`
1. Create a walmartlabs account and aquire and Api Key from https://developer.walmartlabs.com/
    1. terminal: `./gradlew -Dspring.profiles.active=realdev -Dwalmartlabs.apikey={your-api-key} clean build`
1. Run the application: `java -jar -Dspring.profiles.active=realdev -Dwalmartlabs.apikey={your-api-key} build/libs/com-phg-productsapi-0.1.1.jar `
1. Configure intellij for running the application:
    1. Preferences -> Build -> Tools -> Gradle - Gradle VM Options (set as shown above)
    1. Right click on MainApplication.groovy and select run.

# Deployment Pipeline
[Development and Deployment Rhythms](docs/develop-and-deploy.md)

# References
1. White House API Standards informed response wrapper design: https://github.com/WhiteHouse/api-standards
1. Request product by UPC API design: http://stackoverflow.com/questions/20381976/rest-api-design-getting-a-resource-through-rest-with-different-parameters-but
1. Spring Boot
