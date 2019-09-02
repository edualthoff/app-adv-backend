package br.app.adv.main.exception.sql;

import br.app.adv.main.exception.ArgumentValueException;

public class NotEmptySqlException extends ArgumentValueException{
	private static final long serialVersionUID = -5125102200521855422L;

	public NotEmptySqlException(int errorId, String mensagem, String input) {
		super(errorId, mensagem, input);
	}

}
