package com.bridgelabz.movie.responce;

public class ResponseHelper {
	public static Response statusResponse(int code, String message) {
		Response statusResponse = new Response();
		statusResponse.setStatusMessage(message);
		statusResponse.setStatusCode(code);
		return statusResponse;
	}

}