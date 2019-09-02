package br.app.adv.main.security.auth.mediasocial;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.app.adv.main.person.Person;

@Entity
@Table(name = "auth_mediasocial")
public class AuthMediaSocial implements Serializable{
	private static final long serialVersionUID = -2760579984970887542L;

	@Id
	@Column(name="pessoa_id_pessoa", nullable = false)
	private long id;

	@Column(name="tipo")
	private String tipo;
	
	@Column(name="user_id")
	private long userId;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id_pessoa", referencedColumnName = "pessoa_id")
	private Person pessoa;

	public AuthMediaSocial(String tipo, long userId) {
		this.tipo = tipo;
		this.userId = userId;
	}

	public AuthMediaSocial() {}
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Person getPessoa() {
		return pessoa;
	}

	public void setPessoa(Person pessoa) {
		this.pessoa = pessoa;
	}

	public long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthMediaSocial other = (AuthMediaSocial) obj;
		if (id != other.id)
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}	
}
