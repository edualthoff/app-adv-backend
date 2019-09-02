package br.app.adv.main.endereco;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.app.adv.main.endereco.cep.Cep;
import br.app.adv.main.person.client.ClientUser;

@Entity
@Table(name = "endereco_cliente")
public class EnderecoClient extends EnderecoCep implements Serializable {
	private static final long serialVersionUID = 1L;

	@Embeddable
	static class EnderecoClientPK implements Serializable {
		private static final long serialVersionUID = -3277794643914954303L;
		@ManyToOne
		@JoinColumn(name = "cep_id_cep", referencedColumnName = "cep_id")
		private Cep cep;
		@ManyToOne
		@JoinColumn(name = "cliente_id_cliente", referencedColumnName = "cliente_id")
		private ClientUser clientUser;
	
		public EnderecoClientPK() {
		}
		public Cep getCep() {
			return cep;
		}
		public void setCep(Cep cep) {
			this.cep = cep;
		}
		public ClientUser getClientUser() {
			return clientUser;
		}
		public void setClientUser(ClientUser clientUser) {
			this.clientUser = clientUser;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((cep == null) ? 0 : cep.hashCode());
			result = prime * result + ((clientUser == null) ? 0 : clientUser.hashCode());
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
			EnderecoClientPK other = (EnderecoClientPK) obj;
			if (cep == null) {
				if (other.cep != null)
					return false;
			} else if (!cep.equals(other.cep))
				return false;
			if (clientUser == null) {
				if (other.clientUser != null)
					return false;
			} else if (!clientUser.equals(other.clientUser))
				return false;
			return true;
		}
	}
	
	@EmbeddedId
	private EnderecoClientPK idPk;

	public EnderecoClientPK getIdPk() {
		return idPk;
	}

	public void setIdPk(EnderecoClientPK idPk) {
		this.idPk = idPk;
	}
}
