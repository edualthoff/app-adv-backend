package br.app.adv.main.security.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.app.adv.main.person.Person;
import br.app.adv.main.person.PersonTypeEnum;

@Service
public class AuthRolesService implements Serializable{
	private static final long serialVersionUID = 7530979934910995848L;

	private static final Logger log = LogManager.getLogger(AuthRolesService.class);

	@Autowired private AuthRolesDAO authRolesDAO;

	public List<AuthRoles> adicionarRoles(PersonTypeEnum tipoUser, Person p) throws Exception {
		log.debug("Criar as roles do usuario setRoles - {}", tipoUser);
		List<AuthRoles> listAuth = new ArrayList<>();
		AuthRoles auth = new AuthRoles();
		switch (tipoUser) {
		case ADVOGADO:
			auth = new AuthRoles(RolesProfileEnum.ROLE_ADV, p.getId());
			//auth.setRoles(RolesProfileEnum.ROLE_ADV);
			//auth.setPersonId(p.getId());
			listAuth.add(auth);
			break;
		case CLIENTE:
			auth = new AuthRoles(RolesProfileEnum.ROLE_CLIENT, p.getId());
			listAuth.add(auth);
			break;
		default:
			break;
		}
		auth = new AuthRoles(RolesProfileEnum.ROLE_USER, p.getId());
		listAuth.add(auth);
		this.authRolesDAO.saveAll(listAuth);
		return listAuth;
	}
}
