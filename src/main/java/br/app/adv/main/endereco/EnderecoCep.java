package br.app.adv.main.endereco;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EnderecoCep {

	@Column(name="numero")
	private int numero;
	
	@Column(name="apto")
	private int apto;
	
	@Column(name="longitude")
	private int longitude;
	
	@Column(name="latitude")
	private int latitude;

	@Column(name="tipo")
	@Enumerated
	private TipoEnderecoEnum tipo;
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getApto() {
		return apto;
	}

	public void setApto(int apto) {
		this.apto = apto;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public TipoEnderecoEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoEnderecoEnum tipo) {
		this.tipo = tipo;
	}
}
