package br.app.adv.main.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public abstract class ArgumentValueException extends Exception{
	private static final long serialVersionUID = 2357442650170074239L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime data;
	private int errorId;
	private String mensagem;
	private String input;
	
	public ArgumentValueException(int errorId, String mensagem, String input) {
		this.data = LocalDateTime.now();
		this.errorId = errorId;
		this.mensagem = mensagem;
		this.input = input;
	}
	public LocalDateTime getData() {
		return data;
	}
	public void setData(LocalDateTime data) {
		this.data = data;
	}
	public int getErrorId() {
		return errorId;
	}
	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}	
}
