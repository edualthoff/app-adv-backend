package br.app.adv.main.security.auth;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.app.adv.main.person.Person;

@Service
public class AuthRolesService implements Serializable{
	private static final long serialVersionUID = 1039105104475717285L;
	private static final Logger log = LogManager.getLogger(AuthRolesService.class);

	@Autowired private AuthRolesDAO authRolesDAO;

	public AuthRoles adicionarRole(RolesProfileEnum tipoUser, Person p) throws Exception {
		log.debug("Adiciona uma nova role do tipo {} do user {} na base de dados", tipoUser );
		AuthRoles auth = new AuthRoles(tipoUser, p);
		this.authRolesDAO.save(auth);
		return auth;
	}
	public void removerRole(RolesProfileEnum tipoUser, Person p) throws Exception {
		log.debug("Adiciona uma nova role do tipo {} do user {} na base de dados", tipoUser );
		AuthRoles auth = new AuthRoles(tipoUser, p);
		this.authRolesDAO.delete(auth);
	}
	public void saveAll(List<AuthRoles> authR) {
		this.authRolesDAO.saveAll(authR);
	}

	/**
	 * Implementa ROLE_ADV {@link AuthRoles} passando o Id do tipo Person, Client, Advogado.
	 * @param person
	 * @return AuthRoles
	 */
	public AuthRoles roleAdvogado(Person p) {
		AuthRoles auth = new AuthRoles(RolesProfileEnum.ROLE_ADV, p);
		return auth;		
	}
	
	/**
	 * Implementa ROLE_CLIENT {@link AuthRoles} passando o Id do tipo Person, Client, Advogado.
	 * @param person
	 * @return AuthRoles
	 */
	public AuthRoles roleClient(Person p) {
		AuthRoles auth = new AuthRoles(RolesProfileEnum.ROLE_CLIENT, p.getId());
		return auth;
	}
	
	/**
	 * Implementa ROLE_USER {@link AuthRoles} passando o Id do tipo Person, Client, Advogado.
	 * @param person
	 * @return AuthRoles
	 */
	public AuthRoles roleUser(Person p) {
		AuthRoles auth = new AuthRoles(RolesProfileEnum.ROLE_USER, p);
		return auth;
	}
}
