package br.app.adv.main.endereco;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.app.adv.main.endereco.cep.Cep;
import br.app.adv.main.person.adv.AdvogadoUser;

@Entity
@Table(name = "endereco_advogado")
public class EnderecoAdvogado extends EnderecoCep implements Serializable{
	private static final long serialVersionUID = 1879009876214069062L;

	@Embeddable
	static class EnderecoAdvPK implements Serializable {
		private static final long serialVersionUID = 133567875233640342L;

		@ManyToOne
		@JoinColumn(name = "cep_id_cep", referencedColumnName = "cep_id")
		private Cep cep;
		@ManyToOne
		@JoinColumn(name = "pessoa_id_advogado", referencedColumnName = "pessoa_id")
		private AdvogadoUser advUser;
		public Cep getCep() {
			return cep;
		}
		public void setCep(Cep cep) {
			this.cep = cep;
		}
		public AdvogadoUser getAdvUser() {
			return advUser;
		}
		public void setAdvUser(AdvogadoUser advUser) {
			this.advUser = advUser;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((advUser == null) ? 0 : advUser.hashCode());
			result = prime * result + ((cep == null) ? 0 : cep.hashCode());
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
			EnderecoAdvPK other = (EnderecoAdvPK) obj;
			if (advUser == null) {
				if (other.advUser != null)
					return false;
			} else if (!advUser.equals(other.advUser))
				return false;
			if (cep == null) {
				if (other.cep != null)
					return false;
			} else if (!cep.equals(other.cep))
				return false;
			return true;
		}		
	}
	
	@EmbeddedId
	private EnderecoAdvPK idPk;
	

	public EnderecoAdvogado() {
		super();
	}


	public EnderecoAdvPK getIdPk() {
		return idPk;
	}


	public void setIdPk(EnderecoAdvPK idPk) {
		this.idPk = idPk;
	}
}
