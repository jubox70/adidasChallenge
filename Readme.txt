I have developed the solution using Java 17 and spring boot 2.6. For data persistence I have used mysql and kafka as distributed messages system.

All microservices are in the same repository, in this case I think it's better because they aren't for production environment and nobody
will work with them. For production purposes, maybe a repository for each microservice would be better, because each one
is independent from the others.

My subscription solution has 4 microservices. I will try to describe and explain them in the next words.

One of them is to discover and control the others (discoveryService), it's a Eureka server service. This service has http basic authentication because it has a web interface, 
the service is not exposed through gateway service, the port 8084 is enabled in docker-compose. This service is very simple and it doesn't need more explanation. 

The second is the gateway service, it's the Spring Cloud Gateway microservice and it's the gate that communicate the "exterior world" 
with my solution. All petitions are requested to this service, and depending on the security config of this and the others services,
a bearer token is required. This service is configured as resource server, like the other services(more information below), except Discovery Service that has http basic authentication.
This service has data redis reactive for "in memory" storage, it's to control possible attacks like dOs

The third service is the Subscription Service, this service is in charge of receiving the data and do something with that (insert, modify,
delete...), later, a message will be sent to another service to send an email. The Subscription Service acts too as kafka producer. 
The data is stored in a mysql database. The model mapping is done with hibernate, a ORM library. Spring Data is used as repository, and it's in 
charge of making operations with database, in relation with hibernate. The services classes are the service layer that have all the application
logic, they manage the transactions, cache storage... 
All is implemented with Interface Contracts that define the operations, and, in future changes only the classes that implement those interfaces should 
be changed. (ISubscriptionRepository is the interface that use Spring Data and extends JpaRepository. ISubscriptionService is the interface that has 
the business contract and this interface is used by Controller. The ISubscriptionServiceImpl implements the interface ISubscriptionService and has all the logic).
Same issue happens with classes that send messages to kafka, ISubscriptionMessage is a contract interface that is implemented by ISubscriptionMessageImpl
who has all the logic to produce messages for kafka.
The controller is a class that has the @RestController annotation and implements a delete method (DELETE in HTTP), an insert method (POST in HTTP), an
update method (PUT in HTTP), and two get methods (GET in HTTP), one of them is for all subscriptions, and the other is for a single subscription (the subscription id
is received as parameter). A SpringDataRest could be implemented as controller, because it has HAL support, but all methods are exposed by default. I could have deactivated them
but finally I decided to implement a custom controller.
All methods return a HAL JSON, that is implementhed with HATEOAS library and all methods return a error message too when some exception happens, as the best practices of Adidas API Repository says.
The api documentation is available in http://localhost:8080/api/swagger-ui/index.html. The correct way to do the documentation is following the contract-first principle, but I 
think that for this single demo, it was not necessary. 

The modelmapper library is used for mappings between DTO objects (exposed to the exterior) and entities (service and data layer
classes). The entities and DTO are very similar and I don't need complex transformations, but there is a config class with two beans, one of the beans skips the Id value and is used for inserts and updates. 
For validations I use the springBootStarterValidation.

The cache is implemented with ehCache library, that is supported by spring cache and has many more funcionalities. There are two caches, one for single entities and 
other for all entities response. The "single cache" is updated when an entity update happens, delete when a delete happens and when a new entity is saved, it will add to cache.
The "all entities" cache is deleted when an update, insert or delete happens, because of the next query all data need to be updated.

Some integration test and unit test are in test source as example mode. To compile and build the service, a localhost (defined in application-test.yml) ddbb is needed to pass
integration test (The docker-compose included is valid for this purpose).

Three kafka servers and a zookeeper server are in charge of receiving and saving the messages that consumer will request to send emails. They are in docker-compose file, and they don't require anything to use them.

The last service is a kafka consumer service, this service consumes messages from kafka servers and send an email (no email is sent in this service, it's only an interface and a mock implementation). 
This service is very simple too, it only has the consumer configuration and a class that asks for new messages. The service has a config class for security. It's a resource server and has oauth authentication, but
no requests will be done to this service

These four services are in a docker-compose ready to use (docker-compose is in main folder). We can find too, as I mentioned before, three kafka servers and one zookeeper server to 
control the kafka servers, there is a kafdrop server too. This is a visual web to see the kafka servers, topics... 

Another server is in docker-compose, a keycloak server. This server is very important because is the oauth server that emits the tokens and checks the authorizations with the resource
servers (gateway-service and subscription-service). 

This keycloak server is ready to use when docker-compose goes up, the keycloack server imports a json file in import_realm folder (the file is realm-export.json). This file has all the
configuration done (realm, users, client-id..., scope) in relation with all services.

The services have the standard logging system that Spring boot has, but I think a better approach should be to implement another logging system that saves data and metrics to be used later.

Finally, the main folder has four files for jenkins CI/CD, it's only an approximation. I can't test that and I don't know if it's correct,
but the idea is to connect jenkins with the repository, (depending on the configuration, checking each some minutes or programmed times in the day) get the source code, build it, and if the build and
test are ok, make a docker image with a script and pull the image to docker hub. 

In the folder of each service, there is a Dockerfile and a sh script, this Dockerfile builds a docker image, and the script builds and pulls the image to docker hub. This script is called from jenkins pipeline.

NEXT STEPS: 
A lot of configuration is in application.yml of each service, the ideal scenario should be to implement a Spring Cloud Service Configuration. This service gives us the possibility of having all configuration on a place and the capability to modify without restart or do anything on services. 
The routes configuration are in application.yml too, this is not the best choice because it's not scalable and maintainable. The next step should be to
save this information in a database, that permits us to read data in the moment. This gives us the possibility to add, modify, delete... all the configuration routes.
All services should implement an Actuator service, that gives us a lot of information about each service. In a test environment it's not very important, but in a production environment it's important to check
the health and work of the services. We could use the security implemented to protect all this information. 


Below there is some information that could be useful to make some tests



API Documentation: http://localhost:8080/api/swagger-ui/index.html

*Keycloack url: http://localhost:8090/ 
username: admin
password: admin
grant_type: client_credentials
client_id: adidas-challenge-client-id
client_secret: 3od9zDeowL9wLAZ3ys2ZJm1AWT9fl3Dx
scope: openid
username: test
passoword: test


*Kafdrop url: http://localhost:9000/


URL to get token: http://localhost:8080/auth/realms/adidas-challenge-realm/protocol/openid-connect/token

I think these are the most important things that need to be mentioned, but if more explanations are needed, my mail is jubox70@hotmail.com.


* Those url's are public for testing purposes only, but in a production environment, the url's should be private.




