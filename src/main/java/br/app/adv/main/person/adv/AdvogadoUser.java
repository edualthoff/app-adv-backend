package br.app.adv.main.person.adv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.app.adv.main.endereco.EnderecoAdvogado;
import br.app.adv.main.person.Person;

@Entity
@Table(name = "advogado")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"dataUpdate", "dataCreated"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class AdvogadoUser implements Serializable{
	private static final long serialVersionUID = 835443519142396616L;

	@Id
	@Column(name="pessoa_id_pessoa", nullable = false)
	private long id;
	
	@Column(name="numero_oab")
	private String numeroOab;
	
	@Column(name="estado_oab")
	private String estadoOab;
	
	@Column(name="descricao")
	private String descricao;
	
	@Column(name="rate")
	private int rate;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id_pessoa", referencedColumnName = "pessoa_id")
	private Person pessoa;
	
	@OneToMany(mappedBy="idPk.advUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<EnderecoAdvogado> endereco = new ArrayList<>();
	
	@Column(name="valided")
	private boolean valided;

	@Column(name="data_update", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date dataUpdate;
	
	@Column(name="data_created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date dataCreated;

	public String getNumeroOab() {
		return numeroOab;
	}

	public void setNumeroOab(String numeroOab) {
		this.numeroOab = numeroOab;
	}

	public String getEstadoOab() {
		return estadoOab;
	}

	public void setEstadoOab(String estadoOab) {
		this.estadoOab = estadoOab;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public List<EnderecoAdvogado> getEndereco() {
		return endereco;
	}

	public void setEndereco(List<EnderecoAdvogado> endereco) {
		this.endereco = endereco;
	}

	public Date getDataCreated() {
		return dataCreated;
	}

	public void setDataCreated(Date dataCreated) {
		this.dataCreated = dataCreated;
	}

	public long getId() {
		return id;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public Person getPessoa() {
		return pessoa;
	}

	public void setPessoa(Person pessoa) {
		this.pessoa = pessoa;
	}

	public boolean isValided() {
		return valided;
	}

	public void setValided(boolean valided) {
		this.valided = valided;
	}
	public Date getDataUpdate() {
		return dataUpdate;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataCreated == null) ? 0 : dataCreated.hashCode());
		result = prime * result + ((estadoOab == null) ? 0 : estadoOab.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((numeroOab == null) ? 0 : numeroOab.hashCode());
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
		AdvogadoUser other = (AdvogadoUser) obj;
		if (dataCreated == null) {
			if (other.dataCreated != null)
				return false;
		} else if (!dataCreated.equals(other.dataCreated))
			return false;
		if (estadoOab == null) {
			if (other.estadoOab != null)
				return false;
		} else if (!estadoOab.equals(other.estadoOab))
			return false;
		if (id != other.id)
			return false;
		if (numeroOab == null) {
			if (other.numeroOab != null)
				return false;
		} else if (!numeroOab.equals(other.numeroOab))
			return false;
		return true;
	}	
}
