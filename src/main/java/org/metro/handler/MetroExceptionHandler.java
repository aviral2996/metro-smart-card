package org.metro.handler;

import org.metro.exception.InsufficientBalanceException;
import org.metro.exception.InvalidCardException;
import org.metro.exception.MetroRepositoryException;
import org.metro.exception.SwipeInException;
import org.metro.exception.SwipeOutException;
import org.metro.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MetroExceptionHandler {

	@ExceptionHandler(value = InsufficientBalanceException.class)
	public ResponseEntity<ErrorResponse> insufficientBalanceException(InsufficientBalanceException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("Insufficient Balance. Please recharge");
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(value = InvalidCardException.class)
	public ResponseEntity<ErrorResponse> invalidCardException(InvalidCardException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("Invalid Metro Card.");
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = MetroRepositoryException.class)
	public ResponseEntity<ErrorResponse> metroRepositoryException(MetroRepositoryException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("Error occurred while fetching/inserting data into the database");
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = SwipeInException.class)
	public ResponseEntity<ErrorResponse> swipeInException(SwipeInException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("First swipe out and complete the last journey.");
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(value = SwipeOutException.class)
	public ResponseEntity<ErrorResponse> swipeOutException(SwipeOutException exception) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("First swipe in.");
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
	}
}
