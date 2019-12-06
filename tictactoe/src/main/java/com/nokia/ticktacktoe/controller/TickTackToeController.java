package com.nokia.ticktacktoe.controller;

import com.nokia.ticktacktoe.configuration.EnableLogger;
import com.nokia.ticktacktoe.service.TickTackToeService;
import com.nokia.ticktacktoe.vo.GamerDetailsVO;
import com.nokia.ticktacktoe.vo.MoveDetailsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Provides API end points to facilitate playing TickTackToe with computer
 */
@EnableLogger(logArgs = true, logReturns = true)
@RestController
@RequestMapping("/nokia/ticktacktoe")
@Api("Provides API end points to facilitate playing TickTackToe with computer")
public class TickTackToeController {

	@Autowired
	TickTackToeService ticktacktoeService;

	/**
	 * Creates a TickTackToe Game for the given user
	 * 
	 * @param gamerDetailsVO captures user input
	 * @return gameId of the new game created
	 */
	@ApiOperation(value = "Creates a TickTackToe Game for the given user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created game"),
			@ApiResponse(code = 500, message = "Creation of game failed"),
			@ApiResponse(code = 404, message = "The API you were trying to reach is not found") })
	@PostMapping("/game")
	public ResponseEntity<Integer> saveGamerDetails(@RequestBody @Valid GamerDetailsVO gamerDetailsVO) {
		Integer gamerId = ticktacktoeService.saveGamerDetails(gamerDetailsVO);
		return new ResponseEntity<>(gamerId, HttpStatus.CREATED);
	}

	/**
	 * API for getting TickTackToe Game State for the given GameId
	 * 
	 * @param gameId Id of current game
	 * @return TickTackToe Game State for the given GameId
	 */
	@ApiOperation(value = "Returns TickTackToe Game State for the given GameId")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved game state"),
			@ApiResponse(code = 500, message = "Retrieval of game state failed"),
			@ApiResponse(code = 404, message = "The API you were trying to reach is not found") })
	@GetMapping("/game/{gameId}")
	public ResponseEntity<String> getGameState(@PathVariable Long gameId) {
		String gameStateVO = ticktacktoeService.getGameState(gameId);
		return new ResponseEntity<>(gameStateVO, HttpStatus.OK);
	}

	/**
	 * Allows to make a move in TickTackToe Game for the given GameId
	 * 
	 * @param gameId        Id of current game
	 * @param moveDetailsVO captures user move details
	 * @return TickTackToe Game State for the given GameId
	 */
	@ApiOperation(value = "Allows to make a move in TickTackToe Game for the given GameId")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully made move"),
			@ApiResponse(code = 500, message = "Attempt to make a move failed"),
			@ApiResponse(code = 404, message = "The API you were trying to reach is not found") })
	@PostMapping("/game/{gameId}/move")
	public ResponseEntity<String> makeAMove(@PathVariable Long gameId,
			@RequestBody @Valid MoveDetailsVO moveDetailsVO) {
		String gameResponseVO = ticktacktoeService.makeAMove(gameId, moveDetailsVO);
		return new ResponseEntity<>(gameResponseVO, HttpStatus.OK);
	}
}
