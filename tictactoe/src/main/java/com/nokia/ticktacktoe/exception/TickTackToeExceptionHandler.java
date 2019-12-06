package com.nokia.ticktacktoe.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.nokia.ticktacktoe.constants.ErrorConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Exception Handler that handles all exception occurring across the TickTackToe
 * service
 */
@ControllerAdvice
@RestController
public class TickTackToeExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Customize the response for MethodArgumentNotValidException.
	 * 
	 * @param ex      the exception
	 * @param headers the headers to be written to the response
	 * @param status  the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = null;
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		details = errors.values().stream().collect(Collectors.toList());
		TickTackToeErrorResponse tickTackToeErrorResponse = new TickTackToeErrorResponse(
				ErrorConstants.REQUEST_PAYLOAD_VALID, details, 400);
		return handleExceptionInternal(ex, tickTackToeErrorResponse, headers, status, request);
	}

	/**
	 * This is a generic TickTackToeException. This will be used for all custom
	 * exceptions occurring in the service
	 * 
	 * @param ex contains the exception object
	 * @return ResponseEntity object of TickTackToeErrorResponse with appropriate
	 *         error response and Http status
	 */
	@ExceptionHandler(value = { TickTackToeException.class })
	protected ResponseEntity<TickTackToeErrorResponse> handleTickTackToeException(TickTackToeException ex) {
		List<String> details = new ArrayList<>();
		details.add(ex.getStatus().toString());
		TickTackToeErrorResponse tickTackToeErrorResponse = new TickTackToeErrorResponse(ex.getMessage(), details,
				ex.getStatus().value());
		return new ResponseEntity<>(tickTackToeErrorResponse, ex.getStatus());
	}
}
