package com.nokia.ticktacktoe.constants;

/**
 * File for Error constants
 */
public class ErrorConstants {

	private ErrorConstants() {
		super();
	}

	public static final String INVALID_GAME_ID = "InValid Game Id!";
	public static final String GAME_OVER = "Game Over!! ";
	public static final String SAVE_DB_ERROR = "Saving game details failed Due To DB Error :";
	public static final String SAVE_FAILED = "Saving game details failed : ";
	public static final String FETCH_DB_ERROR = "Retrieval of game details failed Due To DB Error : ";
	public static final String FETCH_FAILED = "S\"Retrieval of game details failed : ";
	public static final String MOVE_DB_ERROR = "Saving game details failed Due To DB Error :Operation failed Due To DB Error : ";
	public static final String MOVE_FAILED = "Operation failed : ";
	public static final String MOVE_NOT_VALID = "Move Not Valid!";
	public static final String CHARACTER_NOT_VALID = "Incorrect Character! Please choice character between (x or o) ";
	public static final String REQUEST_PAYLOAD_VALID = "Request Payload Invalid!";
}
