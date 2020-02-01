# Apsis RnD Java assignment

Implement a simple backend application that satisfies the below specifications. You do not need to build any frontend, only an API and underlying system.

## Specifications

Your app should be implemented as a web service designed to be used by multiple clients.

It should expose a few methods: 
  o	one to increment a named counter by 1
  o	one to get the current value of a given counter
  o	one to get a list of all counters and their current value
  o	one to create new counters
 
## Notes

* Try to make the API nice to use for a hypothetical client developer.
* It’s ok if the counters lose state on app shutdown (you don’t need to implement persistent storage layer).
* For this test we ask you to use some version of Java.
* We recommend using a framework like Jersey, but you are free to choose other options.
* Bonus points if you can host a running app somewhere (for example Heroku or AWS) where we can play around with the app a bit


## Additional questions:
*(you do not need to actually implement support for the below items, just have an idea for how the app would be changed to support each one. We will discuss them during a subsequent code review session.)*


* **Persistence**. How would you  add a persistent storage layer such that the app could be restarted without losing counter states? What storage technology would you suggest?
* **Fault tolerance**. How would you design the app in order to make the functionality be available even if some parts of the underlying hardware systems were to fail?
* **Scalability**. How would you design the app need in order to ensure that it wouldn’t slow down or fail if usage increased by many orders of magnitude? Does your choice of persistence layer change in this scenario?
* **Authentication**. How would you ensure that only authorised users can submit and retrieve data?
