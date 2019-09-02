package br.app.adv.main.telefone;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="telefone")
public class Telefone implements Serializable{
	private static final long serialVersionUID = -7983118770398605149L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="telefone_id")
	private long id;
	
	@Column(name="numero")
	private long numero;
	
	@Column(name="tipo")
	private String tipo;
	
	@Column(name="preferencia")
	private boolean preferencia;
	
	@Column(name="verificado")
	private boolean verificado;
	
	public long getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		//System.out.println("Telefone replace patterns "+numero.replaceAll("\\D", ""));
		this.numero = Long.parseLong(numero.replaceAll("\\D", ""));
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isPreferencia() {
		return preferencia;
	}

	public void setPreferencia(boolean preferencia) {
		this.preferencia = preferencia;
	}

	public long getId() {
		return id;
	}
	public boolean isVerificado() {
		return verificado;
	}
	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}
}
