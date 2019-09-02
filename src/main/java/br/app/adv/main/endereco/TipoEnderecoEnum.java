package br.app.adv.main.endereco;

/**
 * Enum com os tipos de endereço dos Usuarios do sistemas..
 */
public enum TipoEnderecoEnum {

	RESIDENCIAL(1), COMERCIAL(2);

	private int tipo;

	private TipoEnderecoEnum(int tipo) {
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}	
}
