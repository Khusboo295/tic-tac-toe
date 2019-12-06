package com.nokia.ticktacktoe.component;

import com.nokia.ticktacktoe.configuration.EnableLogger;
import com.nokia.ticktacktoe.constants.CommonConstants;
import com.nokia.ticktacktoe.constants.ErrorConstants;
import com.nokia.ticktacktoe.domain.TblGamerDetails;
import com.nokia.ticktacktoe.exception.TickTackToeException;
import com.nokia.ticktacktoe.repository.TblGamerDetailsRepository;
import com.nokia.ticktacktoe.utils.MinMaxUtils;
import com.nokia.ticktacktoe.utils.ModelUtils;
import com.nokia.ticktacktoe.vo.GamerDetailsVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * Components class containing methods to support service functionality
 */
@EnableLogger(logArgs = true, logReturns = true)
@Component
public class TickTackToeComponent {

	int user = 1;
	int computer = 2;
	int[][] board = new int[3][3];
	int winner;

	@Autowired
	TblGamerDetailsRepository tblGamerDetailsRepository;

	public void setTblGamerDetailsRepository(TblGamerDetailsRepository tblGamerDetailsRepository) {
		this.tblGamerDetailsRepository = tblGamerDetailsRepository;
	}

	/**
	 * Mapping gamerDetailsVO to entity
	 * 
	 * @param gamerDetailsVO containing gamer Details
	 * @return entity object for TblGamerDetails
	 */
	public TblGamerDetails mapVoToEntity(GamerDetailsVO gamerDetailsVO) {
		TblGamerDetails tblGamerDetails = new TblGamerDetails();
		tblGamerDetails.setGamerName(gamerDetailsVO.getName());
		tblGamerDetails.setGamerCharacter(gamerDetailsVO.getCharacter());
		tblGamerDetails.setGameState(CommonConstants.INITIAL_BOARD_STATE);
		tblGamerDetails.setGameStatus(CommonConstants.INITIAL_BOARD_STATUS);
		return tblGamerDetails;
	}

	/**
	 * Return an X or O, depending upon whose move it was
	 * 
	 * @param charNum contains 1 if 'x' OR 2 id 'o'
	 * @return game character
	 */
	public static String printChar(int charNum) {
		if (1 == charNum) {
			return "x";
		} else if (2 == charNum) {
			return "o";
		} else {
			return " ";
		}
	}

	/**
	 * Print the board
	 * 
	 * @param board      array of game board
	 * @param gameStatus contains game status
	 * @return string board to be displayed
	 */
	public static String printBoard(int[][] board, String gameStatus) {
		StringBuilder diplayBoard = new StringBuilder();
		diplayBoard.append("").append("   A B C\nA |").append(printChar(board[0][0])).append("|")
				.append(printChar(board[0][1])).append("|").append(printChar(board[0][2])).append("|\nB |")
				.append(printChar(board[1][0])).append("|").append(printChar(board[1][1])).append("|")
				.append(printChar(board[1][2])).append("|\nC |").append(printChar(board[2][0])).append("|")
				.append(printChar(board[2][1])).append("|").append(printChar(board[2][2])).append("|      ")
				.append(gameStatus);
		return diplayBoard.toString();
	}

	/**
	 * Applying user's and computer's move, checking game status and saving the game
	 * state and status in database
	 * 
	 * @param move     user move
	 * @param board    current game board
	 * @param turn     containing player turn
	 * @param id       containing game id
	 * @param user     for user player
	 * @param computer for computer player
	 * @return current status of game
	 */
	public String movePlayed(int move, int[][] board, int turn, Long id, int user, int computer) {
		if (turn == user) {
			board[(int) (move / 3)][move % 3] = turn;
		}
		winner = MinMaxUtils.checkWinner(board);
		String gameStatus = CommonConstants.GAME_ONGOING_STATUS;
		if (winner != CommonConstants.NONE) {
			if (winner == user) {
				gameStatus = CommonConstants.HUMAN_WON;
				updateGamerDetails(board, gameStatus, id);
			} else if (winner == computer) {
				gameStatus = CommonConstants.COMPUTER_WON;
				updateGamerDetails(board, gameStatus, id);
			} else {
				gameStatus = CommonConstants.DRAW;
				updateGamerDetails(board, gameStatus, id);
			}
		} else {
			if (turn == user) {
				turn = computer;
				computerMove(board, user, computer);
				gameStatus = movePlayed(move, board, turn, id, user, computer);
				updateGamerDetails(board, gameStatus, id);
			}
		}
		return gameStatus;
	}

	/**
	 * Generate a computer move
	 * 
	 * @param board    current game board
	 * @param user     for user player
	 * @param computer for computer player
	 */
	public static void computerMove(int[][] board, int user, int computer) {
		ModelUtils modelUtils = new ModelUtils();
		ArrayList<Integer> availableMoves = (ArrayList<Integer>) modelUtils.getAvailableMove();
		for (int row = 0; row < board.length; row++) {
			for (int column = 0; column < board[0].length; column++) {
				if (board[row][column] != 0) {
					availableMoves.remove(Integer.valueOf(row * board.length + column));
				}
			}
		}
		modelUtils.setBoard(board);
		modelUtils.setAvailableMove(availableMoves);
		MinMaxUtils.computerMove(modelUtils, computer, user, computer);
	}

	/**
	 * Updating game details
	 * 
	 * @param board      current game board
	 * @param gameStatus current status of game
	 * @param id         containing game id
	 */
	public void updateGamerDetails(int[][] board, String gameStatus, Long id) {
		Optional<TblGamerDetails> tblGamerDetails = tblGamerDetailsRepository.findById(id);
		if (tblGamerDetails.isPresent()) {
			StringBuilder gameState = new StringBuilder();
			gameState.append("");
			for (int x = 0; x < 3; x++) {
				for (int y = 0; y < 3; y++) {
					if (!(x == 2 && y == 2))
						gameState = gameState.append(board[x][y]).append(",");
					else {
						gameState = gameState.append(board[x][y]);
					}
				}
			}
			tblGamerDetails.get().setGameState(gameState.toString());
			tblGamerDetails.get().setGameStatus(gameStatus);
			tblGamerDetailsRepository.save(tblGamerDetails.get());
		} else {
			throw new TickTackToeException(ErrorConstants.INVALID_GAME_ID, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Maps current game state to array
	 * 
	 * @param gameStates current status of game
	 * @return current game board
	 */
	public int[][] mapGameStateToArray(String[] gameStates) {
		final int[] pos = { 0, 0 };
		int[][] boardGame = new int[3][3];
		Arrays.stream(gameStates).forEach(z -> {
			boardGame[pos[0]][pos[1]] = Integer.parseInt(z);
			if (pos[1] < 2) {
				pos[1] = pos[1] + 1;
			} else {
				pos[0] = pos[0] + 1;
				pos[1] = 0;
			}
		});
		return boardGame;
	}
}
