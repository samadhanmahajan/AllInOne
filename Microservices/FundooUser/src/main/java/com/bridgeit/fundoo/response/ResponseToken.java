package com.bridgeit.fundoo.response;

public class ResponseToken {

	public ResponseToken(String statusMessage, int statusCode, String token) {
		this.statusMessage = statusMessage;
		this.statusCode = statusCode;
		this.token = token;
	}

	private String statusMessage;
	private int statusCode;
	private String token;

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "ResponseToken [statusMessage=" + statusMessage + ", statusCode=" + statusCode + ", token=" + token
				+ "]";
	}

	public ResponseToken() {
		super();
		// TODO Auto-generated constructor stub
	}

}