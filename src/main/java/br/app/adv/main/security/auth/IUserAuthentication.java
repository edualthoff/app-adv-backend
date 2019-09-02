package br.app.adv.main.security.auth;

import org.springframework.security.core.Authentication;

import br.app.adv.main.security.jwt.JwtUser;

public interface IUserAuthentication {

    Authentication getAuthentication();
    JwtUser getJwtUserAuthentication();
}
