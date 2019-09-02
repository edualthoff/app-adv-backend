package br.app.adv.main.security.account;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import br.app.adv.main.security.auth.session.AuthSessionActive;
import br.app.adv.main.security.auth.session.AuthSessionActiveController;
import br.app.adv.main.security.jwt.JwtTokenUtil;
import br.app.adv.main.security.model.CurrentUser;

/**
 * Class que compoem os metodos gerais validação e criação do usuario do sistema como - Gerar o Token JWT para acesso ao sistema.
 *  
 * @author Eduardo
 */
@Component
public class AccountMain implements Serializable {
	private static final long serialVersionUID = 5969735010887244725L;

	private static final Logger log = LogManager.getLogger(AccountMain.class);
	
	@Autowired private JwtTokenUtil jwtTokenUtil;
	@Autowired private UserDetailsService userDetailsService;
	@Autowired private AuthSessionActiveController sessionControlle;
	
	/*
	 * Metodo responsavel por gerar o Token - JWT e implementa o CurrenUser
	 */
	public CurrentUser gerarJWT(String email, AuthSessionActive session) {
		log.debug("Gera o Token de acesso ao sistema JWT");
		final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		session = this.sessionControlle.createSession(session, email);
		final String token = jwtTokenUtil.generateToken(userDetails, session.getId());
		
		return new CurrentUser(token);
	}
}
