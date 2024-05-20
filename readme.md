## Task Management Application, Spring Boot
Application for managing tasks for a team or company

## Documentation:

Can be view with Swagger via url http://localhost:8080/swagger-ui/index.html


## Built With
* Spring Boot
* Spring Security
* PostgreS Database
* Maven 
* JWT
* RabbitMQ
* Docker
* Swagger
* Thymeleaf
* HTML
* Javascript

## Test users

`adminuser@mail.com`  password: `AdminPassword1!`  
`testuser@mail.com`  password: `Password1!`  

## Websocket to view newly created Task in real time

Edit "<strong>testwebsocket.html<strong>" with valid jwt token 
Open in your browser this URL to initialize a WebSocket:
http://localhost:8080

New tasks created are printed to web page.