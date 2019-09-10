package br.app.adv.main.person;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import br.app.adv.main.security.auth.AuthRoles;
import br.app.adv.main.telefone.Telefone;
import br.app.adv.main.upload.UploadFile;

@Entity
@Table(name = "pessoa")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(value = {"dataUpdate", "dataSession"}, allowGetters = true)
@EntityListeners(AuditingEntityListener.class)
public class Person implements Serializable{
	private static final long serialVersionUID = 749666508465367900L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pessoa_id")
	private Long id;
	
	@NotBlank(message = "{name.not.blank}")
	@Column(name="nome_comp")
	private String nomeCompleto;

	@Column(name="data_nasc")
	private Date data_nasc;
	
	@NotBlank
	@Column(name="sexo")
	private String sexo;
	
	@Column(name="cpf")
	private String cpf;
	
	@Column(name="email")
    @NotBlank(message = "{email.not.blank}")
    @Email(message = "{email.not.valid}")
	private String email;
	
	@JsonIgnoreProperties({ "password" })
	@Column(name="senha")
	private String senha;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="pessoa_telefone",
	           joinColumns={@JoinColumn(name = "pessoa_id_pessoa")},
	           inverseJoinColumns={@JoinColumn(name = "telefone_id_telefone")})
	private List<Telefone> telefones = new ArrayList<>(); 
	
	@OneToMany(mappedBy = "pessoa",
			orphanRemoval = true, cascade = {CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private List<AuthRoles> authRole = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "upload_id_upload_file", referencedColumnName = "upload_id")
	private UploadFile imagemPerfil;
	
	// Default - False
	@Column(name="verificado")
	private boolean verificado;
	
	@Column(name="data_update", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date dataUpdate;
	
	@Column(name="data_created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date dataCreated;
	
	public Person(String nomeCompleto, Date data_nasc, String sexo, String email) {
		this.nomeCompleto = nomeCompleto;
		this.data_nasc = data_nasc;
		this.sexo = sexo;
		this.email = email;
	}
	public Person() {
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getData_nasc() {
		return data_nasc;
	}
	public void setData_nasc(Date data_nasc) {
		this.data_nasc = data_nasc;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public List<Telefone> getTelefones() {
		return telefones;
	}
	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
	public List<AuthRoles> getAuthRole() {
		return authRole;
	}
	public void setAuthRole(List<AuthRoles> authRole) {
		this.authRole = authRole;
	}
	public UploadFile getImagemPerfil() {
		return imagemPerfil;
	}
	public void setImagemPerfil(UploadFile imagemPerfil) {
		this.imagemPerfil = imagemPerfil;
	}
	public boolean isVerificado() {
		return verificado;
	}
	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}
	public String getNomeCompleto() {
		return nomeCompleto;
	}
	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}
	public Date getDataUpdate() {
		return dataUpdate;
	}
	public Date getDataCreated() {
		return dataCreated;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authRole == null) ? 0 : authRole.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dataCreated == null) ? 0 : dataCreated.hashCode());
		result = prime * result + ((dataUpdate == null) ? 0 : dataUpdate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (verificado ? 1231 : 1237);
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
		Person other = (Person) obj;
		if (authRole == null) {
			if (other.authRole != null)
				return false;
		} else if (!authRole.equals(other.authRole))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
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
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (verificado != other.verificado)
			return false;
		return true;
	}	
}
