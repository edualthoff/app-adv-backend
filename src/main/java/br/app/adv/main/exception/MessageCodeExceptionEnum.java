package br.app.adv.main.exception;

public enum MessageCodeExceptionEnum {

	NOT_EMPTY(2000, ""),
	INVALID_VALUE(2001, "Valor informado é invalido."),
	GENERIC_ERROR(3000, "Ocorreu um error inesperado");
	
	private int idErro;
	private String messageErro;
	public int getIdErro() {
		return idErro;
	}
	public String getMessageErro() {
		return messageErro;
	}
	
	private MessageCodeExceptionEnum(int idErro, String messageErro) {
		this.idErro = idErro;
		this.messageErro = messageErro;
	}
	
}
