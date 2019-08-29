package com.bridgeit.fundoo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)

public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	int code;
	String msg;

	public UserException() {
	}

	public UserException(int code, String msg) {

		this.code = code;
		this.msg = msg;
	}

	public UserException(String msg) {
		this.msg = msg;
	}
}