
#Tic Tac Toe application
##Tech Stack:
* SpringBoot
* MySQL
* Lombok
* Mapstruct
* Spring Data JPA
* H2 in-memory DB
* Mockito
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
* Install Mysql server or simply use docker version, create a database named "tictactoe"
* run the below gradle command to build the whole project: **gradle clean build**
* run the below gradle command to start the spring boot application: **java -jar ./build/libs/tictactoe-0.0.1-SNAPSHOT.jar **


## How to access the spring boot restful application
* You may use postman to access the endpoint
The endpoint providing the calculation function is exposed. The details is as follows:<br>
URL: /calculation<br>
METHOD: POST<br>
PAYLOAD:  {
          	"operand1":4.7,
          	"operation":"+",
          	"operand2":2
          }<br>
ContentType: application/json<br>
Accept: application/json<br>


## Advantages of this application
* Hibernate builds the entity layer to connect MySQL database server
* Spring Data JPA builds the repository layer (DAO) & H2 in-mem database used as Unit Test to test this layer
* Mapstruct used to map data between entities and dtos
* All the exceptions can be centrally handled in one place (ControllerExceptionHandler.java)
* Lombok makes our life easierIt automatically generates getter,setter, constructor, hashcode, log etc.

