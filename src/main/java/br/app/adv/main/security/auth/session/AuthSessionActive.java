package br.app.adv.main.security.auth.session;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.app.adv.main.person.Person;

@Entity
@Table(name = "auth_session")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EntityListeners(AuditingEntityListener.class)
public class AuthSessionActive implements Serializable{
	private static final long serialVersionUID = 3613800394541729197L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="session_id")
	private Long id;
	
	@Column(name="mobile_id")
	private String mobileId;
	
	@Column(name="modelo")
	private String modelo;
	
	@ManyToOne(optional = false, cascade = CascadeType.MERGE)
	@JoinColumn(name = "pessoa_id_pessoa", nullable = false, referencedColumnName = "pessoa_id")
	private Person person;
	
	@Column(name="data_created", updatable = false)
	@JsonIgnoreProperties(value = {"dataCreated"}, allowGetters = true)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date dataCreated;
	
	@Column(name="data_update", updatable = false)
	@JsonIgnoreProperties(value = {"dateUpdate"}, allowGetters = true)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date dateUpdate;
	
	public AuthSessionActive(String mobileId, String modelo, Person person) {
		this.mobileId = mobileId;
		this.modelo = modelo;
		this.person = person;
	}	
	public AuthSessionActive(String mobileId, String modelo) {
		this.mobileId = mobileId;
		this.modelo = modelo;
	}

	public AuthSessionActive() {}
	
	public String getMobileId() {
		return mobileId;
	}

	public void setMobileId(String mobileId) {
		this.mobileId = mobileId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Long getId() {
		return id;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Date getDataCreated() {
		return dataCreated;
	}
	public Date getDateUpdate() {
		return dateUpdate;
	}
}
