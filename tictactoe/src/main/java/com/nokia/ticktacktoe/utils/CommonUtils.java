package com.nokia.ticktacktoe.utils;

import com.nokia.ticktacktoe.constants.CommonConstants;

/**
 * Common Utility class for TickTackToe functionality
 */
public class CommonUtils {

	private CommonUtils() {
		super();
	}

	/**
	 * Check horizontal,vertical to see if the game is over
	 *
	 * @param board containing the game move details
	 * @return integer state of game
	 */
	public static int checkWinner(int[][] board) {
		int columnStatus = checkColumnsForWinner(board);
		if (columnStatus == 1 || columnStatus == 2)
			return columnStatus;
		int rowStatus = checkRowsForWinner(board);
		if (rowStatus == 1 || rowStatus == 2)
			return rowStatus;
		int status = checkDiagonalsForWinner(board);
		if (status == 1 || status == 2)
			return status;
		if (board[0][0] == CommonConstants.EMPTY || board[0][1] == CommonConstants.EMPTY
				|| board[0][2] == CommonConstants.EMPTY || board[1][0] == CommonConstants.EMPTY
				|| board[1][1] == CommonConstants.EMPTY || board[1][2] == CommonConstants.EMPTY
				|| board[2][0] == CommonConstants.EMPTY || board[2][1] == CommonConstants.EMPTY
				|| board[2][2] == CommonConstants.EMPTY)
			return CommonConstants.NONE;
		return CommonConstants.STALEMATE;
	}

	/**
	 * Check diagonals to see if the game is over
	 *
	 * @param board containing the game move details
	 * @return integer state of game
	 */
	public static int checkDiagonalsForWinner(int[][] board) {
		if ((board[0][0] == board[1][1]) && (board[1][1] == board[2][2]))
			return board[0][0];

		if ((board[0][2] == board[1][1]) && (board[1][1] == board[2][0]))
			return board[0][2];
		return CommonConstants.NONE;
	}

	/**
	 * Check rows to see if the game is over
	 *
	 * @param board containing the game move details
	 * @return integer state of game
	 */
	public static int checkRowsForWinner(int[][] board) {
		if ((board[0][0] == board[1][0]) && (board[1][0] == board[2][0]))
			return board[0][0];

		if ((board[0][1] == board[1][1]) && (board[1][1] == board[2][1]))
			return board[0][1];

		if ((board[0][2] == board[1][2]) && (board[1][2] == board[2][2]))
			return board[0][2];
		return CommonConstants.NONE;
	}

	/**
	 * Check columns to see if the game is over
	 *
	 * @param board containing the game move details
	 * @return integer state of game
	 */
	public static int checkColumnsForWinner(int[][] board) {
		if ((board[0][0] == board[0][1]) && (board[0][1] == board[0][2]))
			return board[0][0];

		if ((board[1][0] == board[1][1]) && (board[1][1] == board[1][2]))
			return board[1][0];

		if ((board[2][0] == board[2][1]) && (board[2][1] == board[2][2]))
			return board[2][0];
		return CommonConstants.NONE;
	}
}
