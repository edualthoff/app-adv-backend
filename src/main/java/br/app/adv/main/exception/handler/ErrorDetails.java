package br.app.adv.main.exception.handler;

import java.time.LocalDateTime;

public class ErrorDetails {
	private LocalDateTime timestamp;
	private int status;
	private int erroId;
	private String message;
	private String details;

	public ErrorDetails(LocalDateTime timestamp, String message, int id, String details, int status) {
		super();
		this.timestamp = timestamp;
		this.erroId = id;
		this.message = message;
		this.details = details;
		this.status = status;
	}
	public ErrorDetails(LocalDateTime timestamp, String message, int id, String details) {
		super();
		this.timestamp = timestamp;
		this.erroId = id;
		this.message = message;
		this.details = details;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

	public int getStatus() {
		return status;
	}

	public int getErroId() {
		return erroId;
	}
}
