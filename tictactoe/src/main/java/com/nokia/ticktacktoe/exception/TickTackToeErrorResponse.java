package com.nokia.ticktacktoe.exception;

import java.util.List;

/**
 * Custom Response format DTO to display precise error response information, in
 * case any exception occurs
 */
public class TickTackToeErrorResponse {

	private String message;

	private List<String> details;

	private int errorCode;

	public TickTackToeErrorResponse() {
		super();
	}

	public TickTackToeErrorResponse(String message, List<String> details, int errorCode) {
		super();
		this.message = message;
		this.details = details;
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}