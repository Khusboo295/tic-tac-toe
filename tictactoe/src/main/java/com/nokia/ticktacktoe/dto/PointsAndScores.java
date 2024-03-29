package com.nokia.ticktacktoe.dto;

/**
 * DTO for points and scores information
 */
public class PointsAndScores {

	private int score;
	private Point point;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public PointsAndScores(int score, Point point) {
		this.score = score;
		this.point = point;
	}
}
