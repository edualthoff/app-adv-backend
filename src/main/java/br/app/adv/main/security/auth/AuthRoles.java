package br.app.adv.main.security.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;

import br.app.adv.main.person.Person;


@Entity
@Table(name = "auth_roles")
@IdClass(AuthRoles.AuthRolesPK.class)
public class AuthRoles implements Serializable{
	private static final long serialVersionUID = 5999769049017477670L;

	static class AuthRolesPK implements Serializable {
		private static final long serialVersionUID = -103223968343551112L;
		private RolesProfileEnum roles;
		private Long personId;

		public AuthRolesPK() {}

		public AuthRolesPK(RolesProfileEnum roles, Long personId) {
			this.roles = roles;
			this.personId = personId;
		}
		public RolesProfileEnum getRoles() {
			return roles;
		}
		public void setRoles(RolesProfileEnum roles) {
			this.roles = roles;
		}
		public Long getPersonId() {
			return personId;
		}
		public void setPersonId(Long personId) {
			this.personId = personId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((personId == null) ? 0 : personId.hashCode());
			result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
			AuthRolesPK other = (AuthRolesPK) obj;
			if (personId == null) {
				if (other.personId != null)
					return false;
			} else if (!personId.equals(other.personId))
				return false;
			if (roles != other.roles)
				return false;
			return true;
		}	
	}
		
	@Id
	@Column(name="roles", nullable = false)
	@Enumerated(EnumType.STRING)
	private RolesProfileEnum roles;
	
	@Id
	@Column(name = "pessoa_id_pessoa", nullable = false)
	private Long personId;
	
	@ManyToOne
	@JoinColumn(name = "pessoa_id_pessoa", insertable = false, updatable = false, referencedColumnName = "pessoa_id")
	//@PrimaryKeyJoinColumn(name="pessoa_id_pessoa", referencedColumnName = "pessoa_id")
	private Person pessoa;

	public AuthRoles() {
	}
	
	public AuthRoles(RolesProfileEnum roles) {
		super();
		this.roles = roles;
	}

	
	public AuthRoles(RolesProfileEnum roles, Person pessoa) {
		super();
		this.roles = roles;
		this.personId = pessoa.getId();
		this.pessoa = pessoa;
	}

	public AuthRoles(RolesProfileEnum roles, Long personId) {
		super();
		this.roles = roles;
		this.personId = personId;
	}

	public RolesProfileEnum getRoles() {
		return roles;
	}

	public void setRoles(RolesProfileEnum roles) {
		this.roles = roles;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
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
		int result = 1;
		result = prime * result + ((personId == null) ? 0 : personId.hashCode());
		result = prime * result + ((pessoa == null) ? 0 : pessoa.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
		AuthRoles other = (AuthRoles) obj;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		if (pessoa == null) {
			if (other.pessoa != null)
				return false;
		} else if (!pessoa.equals(other.pessoa))
			return false;
		if (roles != other.roles)
			return false;
		return true;
	}	
}
