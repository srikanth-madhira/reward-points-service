package com.srikanthmadhira.portfolio.pointsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.srikanthmadhira.portfolio.pointsservice.model.response.ResponseModel;

/**
 * 
 * {@link GlobalExceptionHandler} which is an {@link ControllerAdvice}
 * candidate.
 * 
 * This class assists the controller class by scanning the components beans for
 * exceptions and handling them in a centralized location and standardized
 * fashion.
 * 
 * @author SM053453
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * {@link ExceptionHandler} method for handling {@link IllegalArgumentException}
	 * 
	 * @param ex - an instance of {@link IllegalArgumentException}
	 * @return - An object of {@link ResponseEntity} of {@link ResponseModel} that
	 *         contains the error message from the original exception.
	 */
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResponseModel> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
		return new ResponseEntity<>(new ResponseModel(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
