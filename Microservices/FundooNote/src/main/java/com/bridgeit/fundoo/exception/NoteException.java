package com.bridgeit.fundoo.exception;

@SuppressWarnings("serial")
public class NoteException extends RuntimeException{

	int code;
	String msg;
	
	
	public NoteException(String msg) {
		this.msg = msg;
	}


	public NoteException(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	 
}
