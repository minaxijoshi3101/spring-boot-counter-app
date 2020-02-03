# APSIS developer coding assigment

You will find the coding assignment in the ASSIGNMENT.md file.

## DESCRIPTION
Basic Counter API that has 4 endpoints to implement functionalities in the Assignment.

## USAGE
* [Base URL](https://counter-apsis.herokuapp.com/)
* [Swagger Documentation](https://counter-apsis.herokuapp.com/swagger-ui.html#/)
### ENDPOINTS
#### Create a Counter 
* POST https://counter-apsis.herokuapp.com/counters to create a counter, in post body counter object is required {"id":"String", "value": "Long"  }
* Example curl -X POST "https://counter-apsis.herokuapp.com/counters" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"id\": \"counter1\", \"value\": 0}"

#### List All Counters
* GET https://counter-apsis.herokuapp.com/counters
* Example curl -X GET "https://counter-apsis.herokuapp.com/counters" -H "accept: */*"

#### Get a Specific Counter
* https://counter-apsis.herokuapp.com/counters/{counterId}
* Example curl -X GET "https://counter-apsis.herokuapp.com/counters/counter1" -H "accept: */*"

#### Increment a counter
* https://counter-apsis.herokuapp.com/counters{counterId}/increment
* curl -X PUT "https://counter-apsis.herokuapp.com/counters/counter1/increment" -H "accept: */*"






