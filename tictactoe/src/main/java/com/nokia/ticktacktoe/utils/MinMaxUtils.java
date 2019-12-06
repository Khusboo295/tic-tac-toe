package com.nokia.ticktacktoe.utils;

import com.nokia.ticktacktoe.constants.CommonConstants;

/**
 * Implementing minmax algorithm to determine computer's smart move
 */
public class MinMaxUtils {

	/**
	 * Default private constructor
	 */
	private MinMaxUtils() {
		super();
	}

	/**
	 * MiniMax Algorithm, return each node score
	 * 
	 * @param modelUtils input ModelUtils module
	 * @param side       player side
	 * @return evaluation function score
	 */
	public static double computerMove(ModelUtils modelUtils, int side, int user, int computer) {
		if (checkWinner(modelUtils.getBoard()) != 0) {
			return evaluate(modelUtils, user, computer);
		}
		if (side == computer)
			return maxScore(side, modelUtils, user, computer);
		else
			return minScore(side, modelUtils, user, computer);
	}

	/**
	 * MaxScore return max score in the node
	 * 
	 * @param side       player side
	 * @param modelUtils input ModelUtils module
	 * @return Max score for all current nodes
	 */
	private static double maxScore(int side, ModelUtils modelUtils, int user, int computer) {
		double bestScore = Double.NEGATIVE_INFINITY;
		int bestMoveIndex = -1;
		for (Integer Move : modelUtils.getAvailableMove()) {
			ModelUtils copyBoard = modelUtils.copyBoard();
			copyBoard.hashMove(Move, side);
			double score = computerMove(copyBoard, user, user, computer);
			if (score >= bestScore) {
				bestScore = score;
				bestMoveIndex = Move;
			}
		}
		modelUtils.hashMove(bestMoveIndex, side);
		return bestScore;
	}

	/**
	 * MinScore return smallest score in the node
	 * 
	 * @param side  player side
	 * @param model input ModelUtils module
	 * @return Small score for all current nodes
	 */
	private static double minScore(int side, ModelUtils modelUtils, int user, int computer) {
		double bestScore = Double.POSITIVE_INFINITY;
		int bestMoveIndex = -1;
		for (Integer Move : modelUtils.getAvailableMove()) {
			ModelUtils copyBoard = modelUtils.copyBoard();
			copyBoard.hashMove(Move, side);
			double score = computerMove(copyBoard, computer, user, computer);
			if (score <= bestScore) {
				bestScore = score;
				bestMoveIndex = Move;
			}
		}
		modelUtils.hashMove(bestMoveIndex, side);
		return bestScore;
	}

	/**
	 * Evaluate function, if Computer wins return 20, if Human wins return -20,
	 * otherwise return 0
	 * 
	 * @param side       player side
	 * @param modelUtils input ModelUtils module
	 * @return evaluate score
	 */
	private static double evaluate(ModelUtils modelUtils, int user, int computer) {
		if (checkWinner(modelUtils.getBoard()) == computer) {
			return 20;
		} else if (checkWinner(modelUtils.getBoard()) == user) {
			return -20;
		} else {
			return 0;
		}
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
