### Overview
This is my first Spring Boot app started from the lazybones template, which is SUPER cool.

I have several goals:

1. Get a flavor for Spring Boot.
1. Understand if an API (primarily REST) application can easily be test-driven (at least as easy as a spring mvc app)?
    1. What are the challenges?
1. Create a code sample to illustrate my approach to application development. Primarily this means test-first at the black box and unit boundaries.

#### Observations
1. Application Properties testing challenges
    1. Overwriting application properties at the unit level is not straightforward. I've worked in Grails and Holders.flagconfig is easily maninpulated in unit tests.
    1. This is NOT the case with Spring Boot. Though the testing documentation has a brief statement about how delightful EnvironmentTestUtils is to use.
    1. In the name of creating working software in a reasonable amount of time I chose to use a system property. Unit and blackbox testing then was simple.
    1. It was a bit of a bump trying to get the system property entered at the gradle command line passed through to the JVM.


### Work In Progress
#### Stories
1. Epic - Error Handling
   1. Consistently respond with JSON error document regardless of client
      1. Inform user when UPC not valid (handle Bad Requests from semantics)
   1. Responds with Server Error message and code when connection to external system times out.
   1. Responds with Server Error message and code when cannot connect to external system.
   1. Logs meaningful error messages
1. Find by UPC allows more than 100 requests per day
    1. Put api key and secret in secure place
1. /product/{id} returns a document when identifier exists
1. Commits on the master branch result in a CI build
    1. Sematic api key and secret are available in secure way
1. Blackbox tests run against an external version of the application
1. UPC in response document is a number (not a string)
1. Can disable any endpoint at server start up
1. EPIC - Serve more product data
    1. Create data store with core product data
    1. Application fails if not avaiable
    1. etc

#### Done
1. Move semantics api key to system property
   1. System properties pass through gradle to JVM
   1. Readme instructions for running
   1. Squash git commit history which shows the my key in app code
1. Make simple external http call as part of building response
   1. Uses sematic test endpoint (limited to 100 requests per day).
1. Walking skeleton for /v1/products
   1. Prove mocking available for simple controller unit test
   1. Learn spring boot default rendering
1. Find the lazybones template
1. Response product should have descriptions (plural)

### To Run
1. Clone
1. Create account and aquire and Api Key from https://www.semantics3.com/
    1. OR contact me and I will share mine.
1. terminal: `gradle -Dsemantics3.apikey={your-api-key} test`
1. terminal: `gradle -Dsemantics3.apikey={your-api-key} run`
1. Configure intellij for running the application:
    1. Preferences -> Build -> Tools -> Gradle - Gradle VM Options

### References
1. White House API Standards informed response wrapper design: https://github.com/WhiteHouse/api-standards
1. Request product by UPC API design: http://stackoverflow.com/questions/20381976/rest-api-design-getting-a-resource-through-rest-with-different-parameters-but
1. Spring Boot -