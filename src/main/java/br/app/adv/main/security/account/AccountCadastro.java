package br.app.adv.main.security.account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.app.adv.main.exception.MessageCodeExceptionEnum;
import br.app.adv.main.exception.sql.InvalidValueSqlException;
import br.app.adv.main.person.Person;
import br.app.adv.main.person.PersonService;
import br.app.adv.main.person.PersonTypeEnum;
import br.app.adv.main.person.adv.AdvogadoUser;
import br.app.adv.main.person.adv.AdvogadoUserDAO;
import br.app.adv.main.person.client.ClientUser;
import br.app.adv.main.person.client.ClientUserDAO;
import br.app.adv.main.security.auth.AuthRoles;
import br.app.adv.main.security.auth.AuthRolesService;

/** 
 * Valida as informações do usuario e cria um novo cadastro no sistema 
 **/
@Service
public class AccountCadastro implements Serializable{
	private static final long serialVersionUID = 9173376008268410010L;

	private static final Logger log = LogManager.getLogger(AccountCadastro.class);
	
	@Autowired private PersonService personService;
	@Autowired private AdvogadoUserDAO advDAO;
	@Autowired private ClientUserDAO clientDAO;
	@Autowired private AuthRolesService authRoleService;
	@Autowired private AccountMain accountMain;
	
	public String validarPersonMediaSocial(Person p, PersonTypeEnum tipoUser, String email) throws InvalidValueSqlException {
		Person person = new Person();
		if(p.getEmail() != email) {
			person = this.personService.getConsultaEmail(p.getEmail());
			if(person == null) {
				person = this.personService.getConsultaEmail(email);
				p.setId(person.getId());
				this.cadastrar(p, tipoUser);
				return p.getEmail();
			}else {
				throw new InvalidValueSqlException(MessageCodeExceptionEnum.INVALID_VALUE.getIdErro(), MessageCodeExceptionEnum.INVALID_VALUE.getMessageErro(), "email");
			}
		}else {
			person = this.personService.getConsultaEmail(email);
			p.setId(person.getId());
			this.cadastrar(p, tipoUser);
			return p.getEmail();
		}
	}
	public String validarPersonSenha(Person p, PersonTypeEnum tipoUser) throws InvalidValueSqlException {
		Person person = new Person(); 
		person = this.personService.getConsultaEmail(p.getEmail());
		if(person == null) {
			this.cadastrar(p, tipoUser);
			return p.getEmail();
		}else {
			log.debug("Usuario informado ja está cadastrado");
			throw new InvalidValueSqlException(MessageCodeExceptionEnum.INVALID_VALUE.getIdErro(), MessageCodeExceptionEnum.INVALID_VALUE.getMessageErro(), "email");
		}
	}
	private void cadastrar(Person p, PersonTypeEnum tipoUser) {
		Person person = new Person();
		List<AuthRoles> auth = new ArrayList<>();
		p.setSenha(this.accountMain.encriptyPass(p.getSenha()));
		person = this.personService.salvarPessoa(p);
		System.out.println("pessoa "+person.getEmail());
		this.clientDAO.save(new ClientUser(person));

		switch (tipoUser) {
		case ADVOGADO:
			this.advDAO.save(new AdvogadoUser(person));
			auth.add(this.authRoleService.roleAdvogado(person));
			break;
		case CLIENTE:
			auth.add(this.authRoleService.roleUser(person));
			break;
		default:
			break;
		}	
		auth.add(this.authRoleService.roleClient(person));
		this.authRoleService.saveAll(auth);
	}

	private AdvogadoUser advogadoCad(Person p) {
		AdvogadoUser adv = new AdvogadoUser();
		try {
			p.setSenha(this.accountMain.encriptyPass(p.getSenha()));
			adv.setPessoa(p);
			adv = this.advDAO.save(adv);
			p.getAuthRole().add(this.authRoleService.roleClient(adv.getPessoa()));
			p.getAuthRole().add(this.authRoleService.roleAdvogado(adv.getPessoa()));
			adv.setPessoa(p);
			adv = this.advDAO.save(adv);	
		} catch (Exception e) {
			this.advDAO.delete(adv);
		}

		return adv;
	}
	
	private ClientUser clientCad(Person p) {
		ClientUser client = new ClientUser();
		try {
			p.setSenha(this.accountMain.encriptyPass(p.getSenha()));
			client.setPessoa(p);
			log.debug("clientCad - Inseri um novo elemento client no BD");
			client = this.clientDAO.save(client);
			log.debug("clientCad - Inseri as roles do client no BD");
			p.getAuthRole().add(this.authRoleService.roleUser(client.getPessoa()));
			p.getAuthRole().add(this.authRoleService.roleClient(client.getPessoa()));
			client.setPessoa(p);
			client = this.clientDAO.save(client);
		} catch (Exception e) {
			this.clientDAO.delete(client);
		}

		return client;
	}
}
