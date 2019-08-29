package com.bridgeit.fundoo.response;

import org.springframework.stereotype.Component;

@Component
public class Response {

	private String statusMessage;
	private int statusCode;

	public Response(String statusMessage, int statusCode) {
		this.statusMessage = statusMessage;
		this.statusCode = statusCode;
	}

	public Response() {
	}

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

	@Override
	public String toString() {
		return "Response [statusMessage=" + statusMessage + ", statusCode=" + statusCode + "]";
	}

}
