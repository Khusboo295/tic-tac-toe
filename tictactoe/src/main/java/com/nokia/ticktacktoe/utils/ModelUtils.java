package com.nokia.ticktacktoe.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class containing generic board methods and available moves
 */
public class ModelUtils {

	private int[][] board;
	private ArrayList<Integer> availableMove;

	/**
	 * default constructor, generate 3 X 3 char[][] board and an Array list of all
	 * available moves
	 */
	public ModelUtils() {
		board = new int[3][3];
		availableMove = new ArrayList<>();
		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[0].length; j++)
				board[i][j] = 0;
		for (int i = 0; i < board.length * board[0].length; i++) {
			availableMove.add(i);
		}
	}

	/**
	 * Output current game board
	 * 
	 * @return int[][] game board
	 */
	public int[][] getBoard() {
		return board;
	}

	/**
	 * Set current game board
	 * 
	 * @param int[][] game board
	 */
	public void setBoard(int[][] board) {
		this.board = board;
	}

	/**
	 * Set current available moves
	 * 
	 * @param availableMove all available moves
	 */
	public void setAvailableMove(List<Integer> availableMove) {
		this.availableMove = (ArrayList<Integer>) availableMove;
	}

	/**
	 * Output all current available moves
	 * 
	 * @return all available moves
	 */
	public List<Integer> getAvailableMove() {
		return availableMove;
	}

	/**
	 * make valid move on game board and remove available move from availa_move
	 * 
	 * @param row  input row
	 * @param col  input col
	 * @param side player side
	 */
	public void makeMove(int row, int col, int side) {
		board[row][col] = side;
		availableMove.remove(Integer.valueOf(row * board.length + col));
	}

	/**
	 * calculate move from stored availa_move index
	 * 
	 * @param index index from availa_move, index / 3 represents row, index % 3
	 *              represents col
	 * @param side  player side
	 */
	public void hashMove(int index, int side) {
		makeMove(index / 3, index % 3, side);
	}

	/**
	 * Copy the current ModelUtils
	 * 
	 * @return new ModelUtils copied from current ModelUtils
	 */
	public ModelUtils copyBoard() {
		ModelUtils newBoard = new ModelUtils();
		for (int i = 0; i < 3; i++) {
			newBoard.board[i] = this.board[i].clone();
		}
		newBoard.availableMove = new ArrayList<>();
		newBoard.availableMove.addAll(this.availableMove);
		return newBoard;
	}
}
