### Overview
This is a Spring Boot app start from the lazybones template.

### Work In Progress
#### Stories
1. Make simple external http call as part of building response
   1. Uses sematic test endpoint (limited to 100 requests per day). (done)
   1. When external call fails with timeout - do the right thing (so decide what that should be) - we will save retries until later.
1. Epic - Error Handling
   1. Responds with Not Found message and code when query by UPC has no results
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