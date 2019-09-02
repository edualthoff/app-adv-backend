package br.app.adv.main.exception.handler;


public class ErrorResponse {

	private final String message;
	private final int code;
	private final String status;
	private final ErrorDetails errors;
	
	public ErrorResponse(String message, int code, String status, ErrorDetails errors) {
		this.message = message;
		this.code = code;
		this.status = status;
		this.errors = errors;
	}
	public String getMessage() {
		return message;
	}
	public int getCode() {
		return code;
	}
	public String getStatus() {
		return status;
	}
	public ErrorDetails getErrors() {
		return errors;
	}	
}
