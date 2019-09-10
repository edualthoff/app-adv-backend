package br.app.adv.main.person;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PersonService")
public class PersonService implements Serializable{

	private static final long serialVersionUID = 4093161429233243243L;
	
	@Autowired
	private PersonDAO personDAO;
	
	
	public Person getConsultaEmail(String email) {
		return this.personDAO.findByEmail(email);
	}
	public Person salvarPessoa(Person p) {
		return this.personDAO.save(p);
	}
}
