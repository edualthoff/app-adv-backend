package br.app.adv.main.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.app.adv.main.person.Person;
import br.app.adv.main.person.PersonDAO;
import br.app.adv.main.person.adv.AdvogadoUser;
import br.app.adv.main.person.adv.AdvogadoUserDAO;
import br.app.adv.main.person.client.ClientUser;
import br.app.adv.main.person.client.ClientUserDAO;
import br.app.adv.main.security.jwt.JwtUserFactory;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired private PersonDAO personDAO;
	@Autowired private ClientUserDAO clientDAO;
	@Autowired private AdvogadoUserDAO advgDAO;
	private long id;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Person user = personDAO.findByEmail(email);
		//System.out.println("loadUserByUsername "+user.getAuthRole().size()+" "+user.getId());
		
		if (user == null) {			
			throw new UsernameNotFoundException(String.format("O usuario '%s' não foi encontrado ou não existe.", email));
		} else {
			user.getAuthRole().forEach((name) -> {
				switch (name.getRoles()) {
				case ROLE_ADV:
					AdvogadoUser advu = new AdvogadoUser(); 
					advu = advgDAO.findByPessoaId(user.getId());
					//System.out.println("JwtUserDetailsServiceImpl adv "+advu.getId());
					this.id = advu.getId();
					break;
				case ROLE_CLIENT:
					ClientUser clit = new ClientUser();
					clit = clientDAO.findByPessoaId(user.getId());
					//System.out.println("JwtUserDetailsServiceImpl client "+clit.getId());
					this.id = clit.getId();
					break;
				default:
					break;
				}
			});
			System.out.println("JwtUserDetailsServiceImpl "+user.getAuthRole().size()+" "+id);
			return JwtUserFactory.create(user, id);
		}
	}
}
