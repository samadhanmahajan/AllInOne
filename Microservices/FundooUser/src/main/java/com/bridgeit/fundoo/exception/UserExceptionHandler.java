package com.bridgeit.fundoo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bridgeit.fundoo.response.Response;

@ControllerAdvice
public class UserExceptionHandler {

	/**
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception e) {
		Response response = new Response(e.getMessage(), (HttpStatus.INTERNAL_SERVER_ERROR.value()));
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * @param runtimeException
	 * @return
	 */
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Response> userThrownedException(RuntimeException runtimeException) {

		Response response = new Response(runtimeException.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

	}

}
