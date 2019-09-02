package br.app.adv.main.security.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.app.adv.main.security.jwt.JwtUser;

@Component
public class UserAuthentication implements IUserAuthentication {
	private static final Logger log = LogManager.getLogger(UserAuthentication.class);
	
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

	@Override
	public JwtUser getJwtUserAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if((authentication != null) && !(authentication instanceof AnonymousAuthenticationToken)) {
			log.debug("O usuario está authenticado no sistema {}", SecurityContextHolder.getContext().getAuthentication().getClass());
			return ((JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		}
		log.debug("O usuario não está authenticado no sistema");
		return null;
	}
}

