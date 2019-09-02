package br.app.adv.main.person;

public enum PersonTypeEnum {

	CLIENTE("client"), 
	ADVOGADO("advogado"), 
	ASSOCIADO("associado");
	
	private String person;
	
	public String getPerson() {
		return person;
	}

	PersonTypeEnum(String person) {
		this.person = person.toLowerCase();
	}
	public static PersonTypeEnum getPerson(String person) {
		switch (person.toLowerCase()) {
		case "client":
			return CLIENTE;
		case "advogado":
			return ADVOGADO;
		case "associado":
			return ASSOCIADO;
		default:
			return null;
		}
	}
}
