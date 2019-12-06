package com.nokia.ticktacktoe.service;

import com.nokia.ticktacktoe.component.TickTackToeComponent;
import com.nokia.ticktacktoe.configuration.EnableLogger;
import com.nokia.ticktacktoe.domain.TblGamerDetails;
import com.nokia.ticktacktoe.exception.TickTackToeException;
import com.nokia.ticktacktoe.repository.TblGamerDetailsRepository;
import com.nokia.ticktacktoe.vo.GamerDetailsVO;
import com.nokia.ticktacktoe.vo.MoveDetailsVO;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Provides API functionality to facilitate playing TickTackToe with computer
 */
@Service
@EnableLogger(logArgs = true, logReturns = true)
public class TickTackToeService {

	static final int EMPTY = 0;
	int user = 1;
	int computer = 2;

	@Autowired
	TickTackToeComponent tickTackToeComponent;

	@Autowired
	TblGamerDetailsRepository tblGamerDetailsRepository;

	public void setTickTackToeComponent(TickTackToeComponent tickTackToeComponent) {
		this.tickTackToeComponent = tickTackToeComponent;
	}

	/**
	 * Creates a TickTackToe Game for the given user
	 * 
	 * @param gamerDetailsVO contains user game details
	 * @return gameId of the new game created
	 */
	public Integer saveGamerDetails(GamerDetailsVO gamerDetailsVO) {
		try {
			TblGamerDetails tblGamerDetails = tblGamerDetailsRepository
					.save(tickTackToeComponent.mapVoToEntity(gamerDetailsVO));
			return tblGamerDetails.getId().intValue();
		} catch (HibernateException ex) {
			throw new TickTackToeException("Saving game details failed Due To DB Error : " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception ex) {
			throw new TickTackToeException("Saving game details failed : " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Retrieves and returns current game state of given id
	 * 
	 * @param gameId Id of current game
	 * @return TickTackToe Game State for the given GameId
	 */
	public String getGameState(Long gameId) {
		try {
			Optional<TblGamerDetails> tblGamerDetails = tblGamerDetailsRepository.findById(gameId);
			if (tblGamerDetails.isPresent()) {
				String[] gameStates = tblGamerDetails.get().getGameState().split(",");
				int[][] boardGame = tickTackToeComponent.mapGameStateToArray(gameStates);
				return TickTackToeComponent.printBoard(boardGame, tblGamerDetails.get().getGameStatus());
			} else {
				throw new TickTackToeException("InValid Game Id!", HttpStatus.NOT_FOUND);
			}
		} catch (HibernateException ex) {
			throw new TickTackToeException("Retrieval of game details failed Due To DB Error : " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (TickTackToeException ex) {
			throw new TickTackToeException(ex.getMessage(), ex.getStatus());
		} catch (Exception ex) {
			throw new TickTackToeException("Retrieval of game details failed : " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Allows to make a move in TickTackToe Game for the given GameId
	 * 
	 * @param gameId        Id of current game
	 * @param moveDetailsVO contains user move details
	 * @return TickTackToe Game State for the given GameId
	 */
	public String makeAMove(Long gameId, @Valid MoveDetailsVO moveDetailsVO) {
		try {
			TblGamerDetails tblGamerDetails = tblGamerDetailsRepository.findById(gameId).orElse(null);
			if (tblGamerDetails != null) {
				if (tblGamerDetails.getGameStatus().equalsIgnoreCase("Human won!")
						|| tblGamerDetails.getGameStatus().equalsIgnoreCase("Draw!")
						|| tblGamerDetails.getGameStatus().equalsIgnoreCase("Computer won!")) {
					throw new TickTackToeException("Game Over!!", HttpStatus.BAD_REQUEST);
				}
				return move(gameId, moveDetailsVO, tblGamerDetails);
			} else {
				throw new TickTackToeException("InValid Game Id!", HttpStatus.NOT_FOUND);
			}
		} catch (HibernateException ex) {
			throw new TickTackToeException("Operation failed Due To DB Error : " + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (TickTackToeException ex) {
			throw new TickTackToeException(ex.getMessage(), ex.getStatus());
		} catch (Exception ex) {
			throw new TickTackToeException("Operation failed : " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Functional logic to make a move in TickTackToe Game for the given GameId
	 * 
	 * @param gameId          Id of current game
	 * @param moveDetailsVO   contains user move details
	 * @param tblGamerDetails current save game state from DB
	 * @return TickTackToe Game State for the given GameId
	 */
	private String move(Long gameId, MoveDetailsVO moveDetailsVO, TblGamerDetails tblGamerDetails) {
		Map<String, Integer> moveMap = new HashMap<>();
		moveMap.put("AA", 0);
		moveMap.put("AB", 1);
		moveMap.put("AC", 2);
		moveMap.put("BA", 3);
		moveMap.put("BB", 4);
		moveMap.put("BC", 5);
		moveMap.put("CA", 6);
		moveMap.put("CB", 7);
		moveMap.put("CC", 8);

		String[] gameStates = tblGamerDetails.getGameState().split(",");
		int[][] board = tickTackToeComponent.mapGameStateToArray(gameStates);
		String gameResponseVO = null;
		if (moveDetailsVO.getColumn() != null && moveDetailsVO.getRow() != null) {
			String rowColumn = moveDetailsVO.getRow().toUpperCase() + moveDetailsVO.getColumn().toUpperCase();
			if (moveMap.containsKey(rowColumn) && !(moveMap.get(rowColumn) < 0 || moveMap.get(rowColumn) > 8
					|| board[moveMap.get(rowColumn) / 3][moveMap.get(rowColumn) % 3] != EMPTY)) {
				user = tblGamerDetails.getGamerCharacter().equalsIgnoreCase("x") ? 1 : 2;
				computer = user == 1 ? 2 : 1;
				tickTackToeComponent.movePlayed(moveMap.get(rowColumn), board, user, gameId, user, computer);
				gameResponseVO = getGameState(gameId);
			} else {
				throw new TickTackToeException("Move Not Valid!", HttpStatus.BAD_REQUEST);
			}
		}
		user = 1;
		computer = 2;
		return gameResponseVO;
	}
}
