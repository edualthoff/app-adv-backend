package br.app.adv.main.upload;

public enum FileTypeEnum {

	JPG("image/jpg"), PNG("image/png"), PDF("arquivo/pdf"); 
	
	private String tipo;

	FileTypeEnum(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}
}
