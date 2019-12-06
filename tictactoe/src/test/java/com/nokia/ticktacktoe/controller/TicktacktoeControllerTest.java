package com.nokia.ticktacktoe.controller;

import com.nokia.ticktacktoe.service.TickTackToeService;
import com.nokia.ticktacktoe.vo.GamerDetailsVO;
import com.nokia.ticktacktoe.vo.MoveDetailsVO;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

/**
 * Provides JUnit test cases for TickTackToe controller class methods
 */
@RunWith(MockitoJUnitRunner.class)
public class TicktacktoeControllerTest {

	@InjectMocks
	TickTackToeController ticTacKToeController;

	@Mock
	TickTackToeService ticTacToeService;

	/**
	 * Testing positive scenario for SaveGamerDetails controller method with dummy
	 * data
	 */
	@Test
	public void testSaveGamerDetailsPositive() {
		Integer expectedGameId = 1;
		GamerDetailsVO gamerDetailsVO = new GamerDetailsVO("Khusboo", "x");
		Mockito.when(ticTacToeService.saveGamerDetails(Mockito.any(GamerDetailsVO.class))).thenReturn(1);
		ResponseEntity<Integer> response = ticTacKToeController.saveGamerDetails(gamerDetailsVO);
		assertEquals(expectedGameId, response.getBody());
	}

	/**
	 * Testing positive scenario for GetGameState service method with dummy data
	 */
	@Test
	public void testGetGameStatePositive() {
		StringBuilder expectedGameState = new StringBuilder();
		expectedGameState.append("").append("   A B C").append("\n").append("A ").append("|").append(" ").append("|")
				.append(" ").append("|").append(" ").append("|").append("\n").append("B ").append("|").append(" ")
				.append("|").append(" ").append("|").append(" ").append("|").append("\n").append("C ").append("|")
				.append(" ").append("|").append(" ").append("|").append(" ").append("|").append("      ")
				.append("Game Started!!Your Turn!");
		Mockito.when(ticTacToeService.getGameState(1L)).thenReturn(expectedGameState.toString());
		ResponseEntity<String> response = ticTacKToeController.getGameState(1L);
		assertEquals(expectedGameState.toString(), response.getBody());
	}

	/**
	 * Testing positive scenario for MakeAMove service method with dummy data
	 */
	@Test
	public void testMakeAMovePositive() {
		StringBuilder expectedGameState = new StringBuilder();
		MoveDetailsVO moveDetailsVO = new MoveDetailsVO("A", "A");
		expectedGameState.append("").append("   A B C").append("\n").append("A ").append("|").append(" ").append("|")
				.append(" ").append("|").append(" ").append("|").append("\n").append("B ").append("|").append(" ")
				.append("|").append(" ").append("|").append(" ").append("|").append("\n").append("C ").append("|")
				.append(" ").append("|").append(" ").append("|").append(" ").append("|").append("      ")
				.append("Game Started!!Your Turn!");
		Mockito.when(ticTacToeService.makeAMove(Mockito.anyLong(), Mockito.any(MoveDetailsVO.class)))
				.thenReturn(expectedGameState.toString());
		ResponseEntity<String> response = ticTacKToeController.makeAMove(1L, moveDetailsVO);
		assertEquals(expectedGameState.toString(), response.getBody());
	}

}
