package com.nokia.ticktacktoe.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom Exception used across the TickTackToe service module
 */
public class TickTackToeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final HttpStatus status;

	public TickTackToeException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}
}