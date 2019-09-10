package br.app.adv.main.person.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
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
	private static final long serialVersionUID = -1771612489433526467L;

	@Id
	@Column(name="pessoa_id", nullable = false, unique = true)
	@GeneratedValue(generator = "foreign")
	@GenericGenerator(name = "foreign", strategy = "foreign", parameters =  @Parameter(name = "property", value = "pessoa"))
	private Long id;
	
	@Column(name="rate")
	private int rate;
	
	@OneToOne(mappedBy="idPk.clientUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private EnderecoClient endereco;
	
	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    //@JoinColumn(name = "pessoa_id",  referencedColumnName = "pessoa_id")
    @PrimaryKeyJoinColumn(name="pessoa_id", referencedColumnName = "pessoa_id")
	private Person pessoa;
	
	@Column(name="data_update", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date dataUpdate;
	
	@Column(name="data_created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date dataCreated;


	public ClientUser() {
		super();
	}
	
	
	public ClientUser(Person pessoa) {
		super();
		this.pessoa = pessoa;
	}


	public ClientUser(Long id) {
		super();
		this.id = id;
	}

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
	public Date getDataUpdate() {
		return dataUpdate;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getPessoa() {
		return pessoa;
	}

	public void setPessoa(Person pessoa) {
		this.pessoa = pessoa;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dataCreated == null) ? 0 : dataCreated.hashCode());
		result = prime * result + ((dataUpdate == null) ? 0 : dataUpdate.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + rate;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientUser other = (ClientUser) obj;
		if (dataCreated == null) {
			if (other.dataCreated != null)
				return false;
		} else if (!dataCreated.equals(other.dataCreated))
			return false;
		if (dataUpdate == null) {
			if (other.dataUpdate != null)
				return false;
		} else if (!dataUpdate.equals(other.dataUpdate))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (rate != other.rate)
			return false;
		return true;
	}	
}
