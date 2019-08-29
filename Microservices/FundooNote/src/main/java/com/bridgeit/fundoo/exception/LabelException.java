package com.bridgeit.fundoo.exception;

@SuppressWarnings("serial")
public class LabelException extends RuntimeException{
	int code;
	String msg;
	
	
	public LabelException(String msg) {
		super();
		this.msg = msg;
	}


	public LabelException(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
}
