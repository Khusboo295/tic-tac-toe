package com.nokia.ticktacktoe.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import com.nokia.ticktacktoe.component.TickTackToeComponent;
import com.nokia.ticktacktoe.domain.TblGamerDetails;
import com.nokia.ticktacktoe.repository.TblGamerDetailsRepository;
import com.nokia.ticktacktoe.vo.GamerDetailsVO;
import com.nokia.ticktacktoe.vo.MoveDetailsVO;

/**
 * Provides JUnit test cases for TickTackToe service class methods
 */
@RunWith(MockitoJUnitRunner.class)
public class TickTackToeServiceTest {

	@Mock
	TblGamerDetailsRepository tblGamerDetailsRepository;

	@InjectMocks
	private TickTackToeService tickTackToeService;

	/**
	 * Testing positive scenario for SaveGamerDetails service method with dummy data
	 */
	@Test
	public void testSaveGamerDetailsPositive() {
		Integer expectedGameId = 1;
		String initialState = "0,0,0,0,0,0,0,0,0";
		TblGamerDetails gamerDetails = new TblGamerDetails(1L, "Khusboo", "x", initialState,
				"Game Started!!Your Turn!");
		tickTackToeService.setTickTackToeComponent(new TickTackToeComponent());
		GamerDetailsVO gamerDetailsVO = new GamerDetailsVO("Khusboo", "x");
		when(tblGamerDetailsRepository.save(Mockito.any(TblGamerDetails.class))).thenReturn(gamerDetails);
		Integer actualGameId = tickTackToeService.saveGamerDetails(gamerDetailsVO);
		assertEquals(expectedGameId, actualGameId);
	}

	/**
	 * Testing positive scenario for GetGameState service method with dummy data
	 */
	@Test
	public void testGetGameStatePositive() {
		String initialState = "0,0,0,0,0,0,0,0,0";
		TblGamerDetails gamerDetails = new TblGamerDetails(1L, "Khusboo", "x", initialState,
				"Game Started!!Your Turn!");
		when(tblGamerDetailsRepository.findById(1L)).thenReturn(Optional.of(gamerDetails));
		tickTackToeService.setTickTackToeComponent(new TickTackToeComponent());
		StringBuilder expectedGameState = new StringBuilder();
		expectedGameState.append("").append("   A B C").append("\n").append("A ").append("|").append(" ").append("|")
				.append(" ").append("|").append(" ").append("|").append("\n").append("B ").append("|").append(" ")
				.append("|").append(" ").append("|").append(" ").append("|").append("\n").append("C ").append("|")
				.append(" ").append("|").append(" ").append("|").append(" ").append("|").append("      ")
				.append("Game Started!!Your Turn!");
		String gameState = tickTackToeService.getGameState(1L);
		assertEquals(expectedGameState.toString(), gameState);
	}

	/**
	 * Testing positive scenario for MakeAMove service method with dummy data
	 */
	@Test
	public void testMakeAMovePositive() {
		String initialState = "0,0,0,0,0,0,0,0,0";
		TblGamerDetails gamerDetails = new TblGamerDetails(1L, "Khusboo", "x", initialState,
				"Game Started!!Your Turn!");
		MoveDetailsVO moveDetailsVO = new MoveDetailsVO("A", "A");
		when(tblGamerDetailsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(gamerDetails));
		TickTackToeComponent tickTackToeComponent = new TickTackToeComponent();
		tickTackToeComponent.setTblGamerDetailsRepository(tblGamerDetailsRepository);
		tickTackToeService.setTickTackToeComponent(tickTackToeComponent);
		StringBuilder expectedGameState = new StringBuilder();
		expectedGameState.append("").append("   A B C").append("\n").append("A ").append("|").append("x").append("|")
				.append(" ").append("|").append(" ").append("|").append("\n").append("B ").append("|").append(" ")
				.append("|").append(" ").append("|").append(" ").append("|").append("\n").append("C ").append("|")
				.append(" ").append("|").append(" ").append("|").append(" ").append("|").append("      ")
				.append("Game Started!!Your Turn!");
		String gameState = tickTackToeService.makeAMove(1L, moveDetailsVO);
		assertNotNull(gameState);

	}
}
