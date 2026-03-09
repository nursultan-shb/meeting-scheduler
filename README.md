# meeting-scheduler
A simulation of a meeting scheduling platform that supports time slot management and meeting scheduling

## Quick start
### Prerequisites
* Java 17+
* Spring Boot 4.0.3+ 
* Maven 3.9.12+

The service uses H2 in-memory database and can be run locally either using IDE.
To run it using docker-compose, one should first containerize the application.
For that, .jar file is provided in /target directory.

By default, the service is running on the port: http://localhost:16200.

## API Endpoints

* POST /users creates a user
* GET /users/{userId} returns user details

* POST /users/{userId}/slots creates time slots per user
* GET /users/{userId}/slots returns time slots of a user
* DELETE /users/{userId}/slots removes a time slot of a user

* POST /meetings converts a time slot into a meeting

All POST APIs return a created resource identifier in a response's header.


Detailed API documentation is available here: http://localhost:16200/swagger-ui/index.html#/