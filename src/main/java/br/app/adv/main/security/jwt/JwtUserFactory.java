package br.app.adv.main.security.jwt;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.app.adv.main.person.Person;
import br.app.adv.main.security.auth.AuthRoles;

/* 
 * Classe que é responsavel por pegar as credencias de acesso do usuario que é necessario para validar no token e
 * realizar o redirecionamento no sistema.
 * 
 * Classe responsavel por implementar a classes "class JwtUser implements UserDetail", passando as info necessarias.
 * */

public class JwtUserFactory {
	private static final Logger log = LogManager.getLogger(JwtUserFactory.class);
	private JwtUserFactory() {}

	public static JwtUser create(Person person, long id) {
		log.debug("Id Usuario / Person {}", person.getId());

		return new JwtUser(id,
					person.getEmail(),
					person.getSenha(),
					person.isVerificado(),
					mapToGrantedAuthorities(person.getAuthRole())					
					);
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<AuthRoles> authRoles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for(int i = 0; i < authRoles.size(); i++) {
			AuthRoles role = authRoles.get(i);
			authorities.add(new SimpleGrantedAuthority(role.getRoles().toString()));
		}
		return authorities;
	}
}
