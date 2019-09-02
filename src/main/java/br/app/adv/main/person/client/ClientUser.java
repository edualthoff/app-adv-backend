package br.app.adv.main.person.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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

import br.app.adv.main.endereco.EnderecoClient;
import br.app.adv.main.person.Person;

@Entity
@Table(name = "cliente")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"dataUpdate", "dataSession"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class ClientUser implements Serializable {
	private static final long serialVersionUID = 6548763010794865554L;

	@Id
	@Column(name="pessoa_id_pessoa", nullable = false)
	private long id;
	
	@Column(name="rate")
	private int rate;
	
	@OneToOne(mappedBy="idPk.clientUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private EnderecoClient endereco;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "pessoa_id_pessoa", referencedColumnName = "pessoa_id")
	private Person pessoa;
	
	@Column(name="data_update", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date dataUpdate;
	
	@Column(name="data_created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date dataCreated;

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}
	
	public EnderecoClient getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoClient endereco) {
		this.endereco = endereco;
	}

	public Date getDataCreated() {
		return dataCreated;
	}
	public long getId() {
		return id;
	}

	public Person getPessoa() {
		return pessoa;
	}

	public void setPessoa(Person pessoa) {
		this.pessoa = pessoa;
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
		result = prime * result + (int) (id ^ (id >>> 32));
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
		ClientUser other = (ClientUser) obj;
		if (dataCreated == null) {
			if (other.dataCreated != null)
				return false;
		} else if (!dataCreated.equals(other.dataCreated))
			return false;
		if (id != other.id)
			return false;
		return true;
	}	
}
