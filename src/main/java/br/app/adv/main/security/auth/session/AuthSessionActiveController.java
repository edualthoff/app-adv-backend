package br.app.adv.main.security.auth.session;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.app.adv.main.person.Person;
import br.app.adv.main.person.PersonService;

@RestController
@CrossOrigin(origins = "*")
public class AuthSessionActiveController {

	@Autowired AuthSessionActiveDAO authSessionDAO;
	@Autowired PersonService personDAO;
	
	
	public AuthSessionActive createSession(AuthSessionActive session, String email) {
		AuthSessionActive sess = new AuthSessionActive(session.getMobileId(), session.getModelo());
		Person person = this.personDAO.getConsultaEmail(email);
		System.out.println("user create session "+person.getId());
		sess.setPerson(person);
		System.out.println("user create session "+sess.getPerson().getId());
		return this.authSessionDAO.save(sess);
	}
	
	public boolean validedSession(long idSession) {
		return this.authSessionDAO.findById(idSession) != null;
	}
	
	@GetMapping(name="/auth/session")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<AuthSessionActive>> getListSessionUser(@RequestParam("userid") @Validated long userid) {
		List<AuthSessionActive> session = new ArrayList<>();
		session = authSessionDAO.findAllByPersonId(userid);
		return ResponseEntity.ok(session);		
	}
	
}
