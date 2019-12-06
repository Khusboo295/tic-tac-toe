# TickTackToe
Welcome to the TickTackToe Game, a simple web application allowing users to interact with our Game Engine. The goal of this project is to make possible for users to have a richer experience when playing TickTackToe.

# Approach applied for developing this project
* Min-Max Algorithm for smart computer move
* AOP for logging
* Custom Annotations for logging and validation
* Sonar Lint for Code Quality Analysis
* Jacoco for code coverage
* Javadoc for Java source code documentaion
* Swagger UI for API documentation
* Embedded H2 DB
* Exception Handling via custom exception and controller advice
* Docker Deployment via Docker File

# Pre requisite installations:
* [Open JDK 8]
* [Maven 4](https://maven.apache.org/download.cgi)

# For Local set-up
Fork this [repository](https://github.com/Khusboo295/tic-tac-toe.git)

## [ EXPERIMENTAL ] Build & Deploy

#Local commands:
		mvn clean istall
		Run as Java Application

#Docker Commands:
	docker build -f Dockerfile -t ticktacktoe .
	docker images
	docker run -p [port]:[port] ticktacktoe

## Play the Game
	#Default application port: 8080
#Using Swagger
* Start the Game : http://host:port/swagger-ui.html
* Enter your name and choose character: /nokia/ticktacktoe/game

		 Sample Request: {"name" : "Mati", "character" : "x"}
		 Sample Response: 1 (Id generated)
* Check the status: /nokia/ticktacktoe/game/{gameId}

		 gameId: Game Id generated
		 Sample Request URI: /nokia/ticktacktoe/game/1
* Make a move: /nokia/ticktacktoe/game/{gameId}/move

		 Sample Request URI:  /nokia/ticktacktoe/game/1/move
		 Sample Request Body: {"row" : "A", "column" : "B"}
	
#Using PostMan or any HTTP API interacting tool
* Enter your name and choose character: http://host:port/nokia/ticktacktoe/game

		Sample Request: {"name" : "Mati", "character" : "x"}
		Sample Response: 1 (Id generated)
* Check the status: http://host:port/nokia/ticktacktoe/game/{gameId}

		gameId: Game Id generated
		Sample Request URI: /nokia/ticktacktoe/1
* Make a move: http://host:port/nokia/ticktacktoe/game/{gameId}/move

		Sample Request URI:  /game/1/move
		Sample Request Body: {"row" : "A", "column" : "B"}


 


