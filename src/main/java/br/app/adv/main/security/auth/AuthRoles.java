package br.app.adv.main.security.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;

import br.app.adv.main.person.Person;


@Entity
@Table(name = "auth_roles")
@IdClass(AuthRoles.AuthRolesPK.class)
public class AuthRoles implements Serializable{
	private static final long serialVersionUID = -4766943692689585099L;

	static class AuthRolesPK implements Serializable {
		private static final long serialVersionUID = -3930604525300053518L;
		private RolesProfileEnum roles;
		private long personId;

		public AuthRolesPK() {}

		public AuthRolesPK(RolesProfileEnum roles, long personId) {
			this.roles = roles;
			this.personId = personId;
		}
		public RolesProfileEnum getRoles() {
			return roles;
		}
		public void setRoles(RolesProfileEnum roles) {
			this.roles = roles;
		}
		public long getPersonId() {
			return personId;
		}
		public void setPersonId(long personId) {
			this.personId = personId;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (personId ^ (personId >>> 32));
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
			if (personId != other.personId)
				return false;
			if (roles != other.roles)
				return false;
			return true;
		}	
	}
		
	@Id
	@Column(name="roles", nullable = true)
	@Enumerated(EnumType.STRING)
	private RolesProfileEnum roles;
	
	@Id
	@Column(name = "pessoa_id_pessoa", nullable = true)
	private long personId;
	
	@ManyToOne
	@JoinColumn(name = "pessoa_id_pessoa", insertable = false, updatable = false, referencedColumnName = "pessoa_id")
	private Person person;

	public AuthRoles() {
	}

	public AuthRoles(RolesProfileEnum roles, long personId) {
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

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((person == null) ? 0 : person.hashCode());
		result = prime * result + (int) (personId ^ (personId >>> 32));
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
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		if (personId != other.personId)
			return false;
		if (roles != other.roles)
			return false;
		return true;
	}	
}
