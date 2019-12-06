package com.nokia.ticktacktoe.service;

import com.nokia.ticktacktoe.component.TickTackToeComponent;
import com.nokia.ticktacktoe.configuration.EnableLogger;
import com.nokia.ticktacktoe.constants.CommonConstants;
import com.nokia.ticktacktoe.constants.ErrorConstants;
import com.nokia.ticktacktoe.domain.TblGamerDetails;
import com.nokia.ticktacktoe.exception.TickTackToeException;
import com.nokia.ticktacktoe.repository.TblGamerDetailsRepository;
import com.nokia.ticktacktoe.vo.GamerDetailsVO;
import com.nokia.ticktacktoe.vo.MoveDetailsVO;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Provides API functionality to facilitate playing TickTackToe with computer
 */
@Service
@EnableLogger(logArgs = true, logReturns = true)
public class TickTackToeService {

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
		} catch (Exception ex) {
			throw new TickTackToeException(ErrorConstants.SAVE_FAILED + ex.getMessage(),
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
				throw new TickTackToeException(ErrorConstants.INVALID_GAME_ID, HttpStatus.NOT_FOUND);
			}
		} catch (TickTackToeException ex) {
			throw new TickTackToeException(ex.getMessage(), ex.getStatus());
		} catch (Exception ex) {
			throw new TickTackToeException(ErrorConstants.FETCH_FAILED + ex.getMessage(),
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
			if (null != tblGamerDetails) {
				if (tblGamerDetails.getGameStatus().equalsIgnoreCase(CommonConstants.HUMAN_WON)
						|| tblGamerDetails.getGameStatus().equalsIgnoreCase(CommonConstants.DRAW)
						|| tblGamerDetails.getGameStatus().equalsIgnoreCase(CommonConstants.COMPUTER_WON)) {
					throw new TickTackToeException(ErrorConstants.GAME_OVER + tblGamerDetails.getGameStatus(),
							HttpStatus.BAD_REQUEST);
				}
				return move(gameId, moveDetailsVO, tblGamerDetails);
			} else {
				throw new TickTackToeException(ErrorConstants.INVALID_GAME_ID, HttpStatus.NOT_FOUND);
			}
		} catch (TickTackToeException ex) {
			throw new TickTackToeException(ex.getMessage(), ex.getStatus());
		} catch (Exception ex) {
			throw new TickTackToeException(ErrorConstants.MOVE_FAILED + ex.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
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
		if (null != moveDetailsVO.getColumn() && null != moveDetailsVO.getRow()) {
			String rowColumn = moveDetailsVO.getRow().toUpperCase() + moveDetailsVO.getColumn().toUpperCase();
			if (moveMap.containsKey(rowColumn) && !(moveMap.get(rowColumn) < 0 || moveMap.get(rowColumn) > 8
					|| board[moveMap.get(rowColumn) / 3][moveMap.get(rowColumn) % 3] != CommonConstants.EMPTY)) {
				user = tblGamerDetails.getGamerCharacter().equalsIgnoreCase("x") ? 1 : 2;
				computer = user == 1 ? 2 : 1;
				tickTackToeComponent.movePlayed(moveMap.get(rowColumn), board, user, gameId, user, computer);
				gameResponseVO = getGameState(gameId);
			} else {
				throw new TickTackToeException(ErrorConstants.MOVE_NOT_VALID, HttpStatus.BAD_REQUEST);
			}
		}
		user = 1;
		computer = 2;
		return gameResponseVO;
	}
}
