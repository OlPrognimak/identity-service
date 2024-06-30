
# Overview
The project contains two microservices 
 - apikey-auth-provider
 - uaa-server

# Project description
The project based on SpringBoot framework 3.3.1 and Maven build tool

The Maven project includes three maven module:
 - identity-service
 - apikey-auth-provider
 - uaa-server

## identity-service
That is parent project which includes two another projects ````apikey-auth-provider```` and ```uaa-server```.
To build whole project then navigate to this module and run maven command:
````shell
mvn clean install
````

## apikey-auth-provider
This module creates runnable artifact which has controller ```ApikeyAuthProviderController``` with end point method:
```java
 ResponseEntity<String> createToken(@RequestBody TestUser user) 
```
This endpoint produces JWT token from body parameter ```user```.
## uaa-server
This module creates runnable artifact which has controller ```UUAServerController``` with end point method:
```java
 ResponseEntity<String> createToken(@RequestHeader("Secret") String secret)
```
This end point creates instance of ````Testuser```` from accepted parameter ```secret``` then sends the ```user```
object to the ````apikey-auth-provider```` and then retrieves created JWT token back to the customer.

# Testing
## JUnit-5/Integration tests
Each executable module contains SpringBoot tests.

The module ```uaa-server``` contains also one integration test ```UUAServerControllerIT``` which 
require physical starting of ````apikey-auth-provider```` microservice.

The module ```uaa-server``` contains integration test ```UUAServerControllerTest``` which is not require starting 
additionally ````apikey-auth-provider```` microservice because the request and response to that microservice 
is simulated with test STUB of integrated Wiremock server.

## Test with using Intellij
Got to the ````Tools -> HTTP Client ->Create request in HTTP client````
and copy that script:
```http request
POST http://localhost:8082/uaaserver/createtoken
Accept: *
Content-Type: text/plain
Secret: dGVzdFVzZXJOYW1lOnRlc3RQYXNzd29yZA==
```
If you run application on MAC or Linux/Unix or for example ````Git Bash```` on Windows:
````http request
curl -X POST http://localhost:8082/uaaserver/createtoken \
     -H "Accept: */*" \
     -H "Content-Type: text/plain" \
     -H "Secret: dGVzdFVzZXJOYW1lOnRlc3RQYXNzd29yZA=="
````

The coverage Unit tests for this project are not provided because all code here can be easy covered with 
spring boot test and tests with Wiremock server.





 


