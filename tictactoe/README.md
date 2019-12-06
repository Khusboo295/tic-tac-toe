# TickTackToe
Welcome to the TickTackToe Game, a simple web application allowing users to interact with our Game Engine. The goal of this project is to make possible for users to have a richer experience when playing TickTackToe. 

# Pre requisite installations:
* [Open JDK 8]
* [Maven 4](https://maven.apache.org/download.cgi)

# Local set-up
Fork this [repository](https://bitbucket-eng-bgl1.cisco.com/bitbucket/scm/ces-v2/ces_auth.git)

## [ EXPERIMENTAL ] Build & Deploy
```
Commands:
docker build -f Dockerfile -t ticktacktoe .
docker images
docker run -p [port]:[port] ticktacktoe
```
## Play the Game

#Using Swagger
	# Start the Game : http://localhost:8080/swagger-ui.html
	# Enter your name and choose character: /nokia/ticktacktoe/game
		Sample Request: {"name" : "Mati", "character" : "x"}
		Sample Response: 1 (Id generated)
	#Check the status: /nokia/ticktacktoe/{gameId}
		gameId: Game Id generated
		Sample Request URI: /nokia/ticktacktoe/1
	#Make a move: /game/{gameId}/move
		Sample Request URI:  /game/1/move
		Sample Request Body: {"row" : "A", "column" : "B"}
	
#Using PostMan or any HTTP API interacting tool
	# Enter your name and choose character: http://localhost:8080/nokia/ticktacktoe/game
		Sample Request: {"name" : "Mati", "character" : "x"}
		Sample Response: 1 (Id generated)
	#Check the status: http://localhost:8080/nokia/ticktacktoe/{gameId}
		gameId: Game Id generated
		Sample Request URI: /nokia/ticktacktoe/1
	#Make a move: http://localhost:8080/game/{gameId}/move
		Sample Request URI:  /game/1/move
		Sample Request Body: {"row" : "A", "column" : "B"}


 


