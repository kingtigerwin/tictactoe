
#Tic Tac Toe application
##Tech Stack:
* SpringBoot
* Docker
* MySQL
* Lombok
* Mapstruct
* Spring Data JPA
* H2 in-memory DB
* Mockito
* MockMvc
* Swagger

## Introduction
###This is the springBoot based RESTful API. It supports the below functions:

* Create a new game
* List all games
* Find a game via id
* Find a game via game name
* Perform a move in a game & determine the status of a game & return the winner if there is one

## How to run this application

* Navigate the the root folder /tictactoe under the command line
* run the below gradle command to build the whole project: **gradle clean build**
* run the below gradle command to start the spring boot application: **java -jar ./build/libs/tictactoe-0.0.1-SNAPSHOT.jar**

## This application by default is using h2. I also prepared MySQL version. Please follow below steps if you want to try MySQL version

* Navigate the the root folder /tictactoe under the command line
* rename application.properties to another name e.g. application_backup.properties
* rename application-mysql.properties to application.properties
* run command to docker-compose up
* run the below gradle command to build the whole project: **gradle clean build**
* when the above step is done, run the command **java -jar ./build/libs/tictactoe-0.0.1-SNAPSHOT.jar**


## How to access the spring boot restful application
### You may access swagger doc by accessing below url once application is up and running
http://localhost:8080/swagger-ui.html
<img src=".github/swagger-summary.png" width="300" height="200"/>
* You may use postman to access the endpoint



## Advantages of this application
* Hibernate builds the entity layer to connect MySQL database server
* Spring Data JPA builds the repository layer (DAO) & H2 in-mem database used as Unit Test to test this layer
* Mapstruct used to map data between entities and dtos
* All the exceptions can be centrally handled in one place (ControllerExceptionHandler.java)
* Lombok makes our life easierIt automatically generates getter,setter, constructor, hashcode, log etc.

