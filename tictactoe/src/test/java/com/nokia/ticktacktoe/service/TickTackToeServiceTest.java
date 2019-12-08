package com.nokia.ticktacktoe.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.hibernate.HibernateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.nokia.ticktacktoe.component.AlphaBetaComponent;
import com.nokia.ticktacktoe.component.TickTackToeComponent;
import com.nokia.ticktacktoe.domain.TblGamerDetails;
import com.nokia.ticktacktoe.exception.TickTackToeException;
import com.nokia.ticktacktoe.repository.TblGamerDetailsRepository;
import com.nokia.ticktacktoe.vo.GamerDetailsVO;
import com.nokia.ticktacktoe.vo.MoveDetailsVO;

/**
 * Provides JUnit test cases for TickTackToe service class methods
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = TickTackToeService.class)
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
	 * Testing negative scenario for SaveGamerDetails service method when
	 * HibernateException occurs
	 */
	@Test(expected = TickTackToeException.class)
	public void testSaveGamerDetailsNegative() {
		tickTackToeService.setTickTackToeComponent(new TickTackToeComponent());
		GamerDetailsVO gamerDetailsVO = new GamerDetailsVO("Khusboo", "x");
		when(tblGamerDetailsRepository.save(Mockito.any(TblGamerDetails.class))).thenThrow(HibernateException.class);
		tickTackToeService.saveGamerDetails(gamerDetailsVO);
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
	 * Testing negative scenario for GetGameState service method when
	 * HibernateException occurs
	 */
	@Test(expected = TickTackToeException.class)
	public void testGetGameStateNegative() {
		when(tblGamerDetailsRepository.findById(1L)).thenThrow(HibernateException.class);
		tickTackToeService.setTickTackToeComponent(new TickTackToeComponent());
		tickTackToeService.getGameState(1L);
	}

	/**
	 * Testing negative scenario for GetGameState service method when
	 * TickTackToeException occurs
	 */
	@Test(expected = TickTackToeException.class)
	public void testGetGameStateNegativeWhenInvalidGameId() {
		TblGamerDetails tblGamerDetails = null;
		when(tblGamerDetailsRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(tblGamerDetails));
		tickTackToeService.getGameState(1L);
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
		tickTackToeComponent.setAlphaBetaComponent(new AlphaBetaComponent());
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

	/**
	 * Testing negative scenario for MakeAMove service method when
	 * HibernateException occurs
	 */
	@Test(expected = TickTackToeException.class)
	public void testMakeAMoveNegative() {
		MoveDetailsVO moveDetailsVO = new MoveDetailsVO("A", "A");
		when(tblGamerDetailsRepository.findById(Mockito.anyLong())).thenThrow(HibernateException.class);
		TickTackToeComponent tickTackToeComponent = new TickTackToeComponent();
		tickTackToeComponent.setTblGamerDetailsRepository(tblGamerDetailsRepository);
		tickTackToeService.setTickTackToeComponent(tickTackToeComponent);
		tickTackToeService.makeAMove(1L, moveDetailsVO);
	}

	/**
	 * Testing negative scenario for MakeAMove service method when
	 * TickTackToeException occurs
	 */
	@Test(expected = TickTackToeException.class)
	public void testMakeAMoveNegativeWhenInvalidGameId() {
		MoveDetailsVO moveDetailsVO = new MoveDetailsVO("A", "A");
		when(tblGamerDetailsRepository.findById(Mockito.anyLong())).thenReturn(null);
		TickTackToeComponent tickTackToeComponent = new TickTackToeComponent();
		tickTackToeComponent.setTblGamerDetailsRepository(tblGamerDetailsRepository);
		tickTackToeService.setTickTackToeComponent(tickTackToeComponent);
		tickTackToeService.makeAMove(1L, moveDetailsVO);
	}
}
