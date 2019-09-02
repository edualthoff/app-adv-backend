package br.app.adv.main.security.account;

import java.io.Serializable;

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
		switch (tipoUser) {
		case ADVOGADO:
			this.advogadoCad(p);
			break;
		case CLIENTE:
			this.clientCad(p);
			break;
		default:
			break;
		}		
	}

	private AdvogadoUser advogadoCad(Person p) {
		AdvogadoUser adv = new AdvogadoUser();
		//person = this.personService.setSalvarPessoa(p);
		p.setSenha(this.encriptyPass(p.getSenha()));
		adv.setPessoa(p);
		adv = this.advDAO.save(adv);
		try {
			p.getAuthRole().addAll(this.authRoleService.adicionarRoles(PersonTypeEnum.ADVOGADO, adv.getPessoa()));
		} catch (Exception e) {
			this.advDAO.delete(adv);
		}
		return adv;
	}
	
	private ClientUser clientCad(Person p) {
		ClientUser client = new ClientUser();
		//person = this.personService.setSalvarPessoa(p);
		p.setSenha(this.encriptyPass(p.getSenha()));
		client.setPessoa(p);
		log.debug("clientCad - Inseri um novo elemento client no BD");
		client = this.clientDAO.save(client);
		log.debug("clientCad - Set roleAuth na Class Person");
		try {
			p.getAuthRole().addAll(this.authRoleService.adicionarRoles(PersonTypeEnum.CLIENTE, client.getPessoa()));
		} catch (Exception e) {
			this.clientDAO.delete(client);
		}
		return client;
	}

	private String encriptyPass(String senha) {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		return bc.encode(senha);
	}
}
