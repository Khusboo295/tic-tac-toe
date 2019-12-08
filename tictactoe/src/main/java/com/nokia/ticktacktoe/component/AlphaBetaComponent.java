package com.nokia.ticktacktoe.component;

import com.nokia.ticktacktoe.configuration.EnableLogger;
import com.nokia.ticktacktoe.dto.Point;
import com.nokia.ticktacktoe.dto.PointsAndScores;
import com.nokia.ticktacktoe.utils.CommonUtils;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@EnableLogger(logArgs = true, logReturns = true)
@Component
public class AlphaBetaComponent {

	List<Point> availablePoints;
	int[][] board = new int[3][3];

	public void setBoard(int[][] board) {
		this.board = board;
	}

	int uptoDepth = -1;
	List<PointsAndScores> rootsChildrenScore = new ArrayList<>();

	/**
	 * Evaluating Board to calculate the score
	 * 
	 * @param user
	 * @param computer
	 * @return score
	 */
	public int evaluateBoard(int user, int computer) {
		int score = 0;
		score = checkAllRows(score, user);
		score = checkAllColumns(score, user);
		score = checkDiagonalFirst(score, user, computer);
		score = checkDiagonalSecond(score, user, computer);
		return score;
	}

	/**
	 * alpha Beta pruning to optimize Minimax algorithm
	 * 
	 * @param alpha
	 * @param beta
	 * @param depth
	 * @param turn
	 * @param user
	 * @param computer
	 * @return min or max value
	 */
	public int alphaBetaMinimax(int alpha, int beta, int depth, int turn, int user, int computer) {
		if (beta <= alpha) {
			if (turn == computer)
				return Integer.MAX_VALUE;
			else
				return Integer.MIN_VALUE;
		}

		if (depth == uptoDepth || CommonUtils.checkWinner(board) == user || CommonUtils.checkWinner(board) == computer
				|| getAvailableStates().isEmpty())
			return evaluateBoard(user, computer);

		List<Point> pointsAvailable = getAvailableStates();
		if (pointsAvailable.isEmpty())
			return 0;

		return currentScore(alpha, beta, depth, turn, user, computer, pointsAvailable);
	}

	/**
	 * Returning all available states on board
	 * 
	 * @return list of blank points on board
	 */
	public List<Point> getAvailableStates() {
		availablePoints = new ArrayList<>();
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 3; ++j) {
				if (board[i][j] == 0) {
					availablePoints.add(new Point(i, j));
				}
			}
		}
		return availablePoints;
	}

	/**
	 * Executing move on board
	 * @param point
	 * @param player
	 */
	public void placeAMove(Point point, int player) {
		board[point.getRow()][point.getColumn()] = player;
	}

	/**
	 * Calculating Best Move
	 * @return Best Move
	 */
	public Point bestMove() {
		int max = -100000;
		int best = -1;

		for (int i = 0; i < rootsChildrenScore.size(); ++i) {
			if (max < rootsChildrenScore.get(i).getScore()) {
				max = rootsChildrenScore.get(i).getScore();
				best = i;
			}
		}
		return rootsChildrenScore.get(best).getPoint();
	}

	/**
	 * Check all rows
	 * @param score
	 * @param user
	 * @return score
	 */
	private int checkAllRows(int score, int user) {
		for (int row = 0; row < 3; ++row) {
			int userCount = 0;
			int computerCount = 0;
			for (int column = 0; column < 3; ++column) {
				if (board[row][column] == user) {
					userCount++;
				} else {
					computerCount++;
				}

			}
			score += changeInScore(computerCount, userCount);
		}
		return score;
	}

	/**
	 * Check all columns
	 * @param score
	 * @param user
	 * @return score
	 */
	private int checkAllColumns(int score, int user) {
		for (int column = 0; column < 3; ++column) {
			int computerCount = 0;
			int userCount = 0;
			for (int row = 0; row < 3; ++row) {
				if (board[row][column] == user) {
					userCount++;
				} else {
					computerCount++;
				}
			}
			score += changeInScore(computerCount, userCount);
		}
		return score;
	}

	/**
	 * Check all diagonal (first)
	 * @param score
	 * @param user
	 * @param computer
	 * @return score
	 */
	private int checkDiagonalFirst(int score, int user, int computer) {
		int computerCount = 0;
		int userCount = 0;
		for (int row = 0, column = 0; row < 3; ++row, ++column) {
			if (board[row][column] == user) {
				userCount++;
			} else if (board[row][column] == computer) {
				computerCount++;
			}
		}
		score += changeInScore(computerCount, userCount);
		return score;
	}

	/**
	 * Check Diagonal (Second)
	 * @param score
	 * @param user
	 * @param computer
	 * @return score
	 */
	private int checkDiagonalSecond(int score, int user, int computer) {
		int computerCount = 0;
		int userCount = 0;
		for (int row = 2, column = 0; row > -1; --row, ++column) {
			if (board[row][column] == user) {
				userCount++;
			} else if (board[row][column] == computer) {
				computerCount++;
			}
		}
		score += changeInScore(computerCount, userCount);
		return score;
	}

	/**
	 * Calculate return change in score
	 * @param computerCount
	 * @param userCount
	 * @return change in score
	 */
	private int changeInScore(int computerCount, int userCount) {
		int change;
		if (computerCount == 3) {
			change = 100;
		} else if (computerCount == 2 && userCount == 0) {
			change = 10;
		} else if (computerCount == 1 && userCount == 0) {
			change = 1;
		} else if (userCount == 3) {
			change = -100;
		} else if (userCount == 2 && computerCount == 0) {
			change = -10;
		} else if (userCount == 1 && computerCount == 0) {
			change = -1;
		} else {
			change = 0;
		}
		return change;
	}
 
	/**
	 * Calculate and return Current Score
	 * @param alpha
	 * @param beta
	 * @param depth
	 * @param turn
	 * @param user
	 * @param computer
	 * @param pointsAvailable
	 * @return current Score
	 */
	private int currentScore(int alpha, int beta, int depth, int turn, int user, int computer,
			List<Point> pointsAvailable) {
		if (depth == 0)
			rootsChildrenScore.clear();

		int maxValue = Integer.MIN_VALUE;
		int minValue = Integer.MAX_VALUE;

		for (int i = 0; i < pointsAvailable.size(); ++i) {
			Point point = pointsAvailable.get(i);
			int currentScore = 0;
			if (turn == computer) {
				placeAMove(point, computer);
				currentScore = alphaBetaMinimax(alpha, beta, depth + 1, user, user, computer);
				maxValue = Math.max(maxValue, currentScore);
				alpha = Math.max(currentScore, alpha);
				if (depth == 0)
					rootsChildrenScore.add(new PointsAndScores(currentScore, point));
			} else if (turn == user) {
				placeAMove(point, user);
				currentScore = alphaBetaMinimax(alpha, beta, depth + 1, computer, user, computer);
				minValue = Math.min(minValue, currentScore);
				beta = Math.min(currentScore, beta);
			}
			board[point.getRow()][point.getColumn()] = 0;
			if (currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE)
				break;
		}
		return turn == computer ? maxValue : minValue;
	}

}
