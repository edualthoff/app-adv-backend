package br.app.adv.main.exception;

public class GenericErrorException extends ArgumentValueException {
	private static final long serialVersionUID = -7775424909440981266L;

	public GenericErrorException(int errorId, String mensagem, String input) {
		super(errorId, mensagem, input);
	}

}
