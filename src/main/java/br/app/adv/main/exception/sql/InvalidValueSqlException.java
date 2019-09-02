package br.app.adv.main.exception.sql;

import br.app.adv.main.exception.ArgumentValueException;

public class InvalidValueSqlException extends ArgumentValueException {
	private static final long serialVersionUID = 3448900989531076734L;

	public InvalidValueSqlException(int errorId, String mensagem, String input) {
		super(errorId, mensagem, input);
	}


}
