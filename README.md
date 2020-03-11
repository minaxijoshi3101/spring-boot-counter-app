# Spring Boot Basic Counter Application

A simple backend application that satisfies the below specifications.

## Specifications
Exposing methods below;

* one to create new counters
* one to increment a named counter by 1
* one to get the current value of a given counter
* one to get a list of all counters and their current value


## NOTES
* It looses state on app shutdown (No persistent storage layer).
* Java 11 is used as version.

## USAGE
Application is deployed to Heroku, so the swagger documentation is reachable and endpoints can be tried out.
* [Base URL](https://dry-dusk-65116.herokuapp.com/)
* [Swagger Documentation](https://dry-dusk-65116.herokuapp.com/swagger-ui.html)
### ENDPOINTS
#### Create a Counter 
* POST https://dry-dusk-65116.herokuapp.com/counters to create a counter, in post body counter object is required {"id":"String", "value": "Long"  }
* Example curl -X POST "https://dry-dusk-65116.herokuapp.com/counters" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"id\": \"counter1\", \"value\": 0}"

#### List All Counters
* GET https://dry-dusk-65116.herokuapp.com/counters
* Example curl -X GET "https://dry-dusk-65116.herokuapp.com/counters" -H "accept: */*"

#### Get a Specific Counter
* https://dry-dusk-65116.herokuapp.com/counters/{counterId}
* Example curl -X GET "https://dry-dusk-65116.herokuapp.com/counters/counter1" -H "accept: */*"

#### Increment a counter
* https://dry-dusk-65116.herokuapp.com/counters{counterId}/increment
* curl -X PUT "https://dry-dusk-65116.herokuapp.com/counters/counter1/increment" -H "accept: */*"

## Question on How to Improve
* **Persistence**.
*Persistent storage is needed so as not to loose counter states in restarts. What storage technology can be used?*<br/><br/>
When choosing storage technology, it is important to consider the aim of the counter application. No-Sql Databases are better in terms
of scalability, since it is easier to scale horizontally where sql storages are harder to scale. However, No-SQL Databases are not fully ACID compliant, so in terms of 
consistency, there might be write-write or read-write conflicts resulting different threads incrementing wrong value and there might be slight over-counting or under-counting of the counters. 
This is tolerable if it is used as a counter for web-site visitor. However, if we need integrity of the counter values, for example if we are charging customers based on counter value and if we need exact values, SQL Databases are more
preferable since they are ACID compliant.<br/><br/>
 * **Fault tolerance**. 
 *How to design the app in order to make the functionality be available even if some parts of the underlying hardware systems were to fail?*<br/><br/>
 At least two instances of the services should be running and database needs to be replicated as well to cover fail-over scenario. This app does not call any other service, but if it was, cascading failure scenario should be handled for example
 circuit breakers can be applied. <br/><br/>
* **Scalability**. 
*How to design the app need in order to ensure that it wouldn’t slow down or fail if usage increased by many orders of magnitude?*<br/><br/>
Application should be scaled horizontally. In that case, a load balancer is required in front of the services. Load balancer should direct the requests to the service, where the specific counter exists. So, basically, load balancer should calculate hash(counterId)%noOfServices so as to determine to which service to divert request.
Database also should be scaled. No-SQL Databases are easy to scale horizantally. SQL databases can be scaled by sharding too. So, for counter application, database can be sharded by the counterId. <br/><br/>
* **Authentication**. 
*How to ensure that only authorised users can submit and retrieve data?*<br/><br/>
An authentication and authorization mechanism is used. Basic authentication or cookie-based session mechanism can be used basically. But, in a distributed systems Oauth and JWT can be used.
<br/><br/>
* **Coding**.<br/><br/>
 Model and DTO Objects should be separated and Mapper can be used to convert objects. Some codes in Repository layer can be moved to service layer so as to make repository layer more generic. 
 There are other improvements in the code as marked as TODO



