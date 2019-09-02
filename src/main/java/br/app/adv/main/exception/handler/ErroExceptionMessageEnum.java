package br.app.adv.main.exception.handler;

public enum ErroExceptionMessageEnum {

	AuthenticationException(1000, "Credenciais Invalida"), 
	SQLGrammarException(1001, "Error interno, não foi possivel acessar o banco de dados");

	private String description;
	private int id;

	ErroExceptionMessageEnum(int id, String description) {
		this.description = description;
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}
}
