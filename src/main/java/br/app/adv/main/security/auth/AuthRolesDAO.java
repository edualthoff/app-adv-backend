package br.app.adv.main.security.auth;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.app.adv.main.security.auth.AuthRoles.AuthRolesPK;


@Repository
public interface AuthRolesDAO extends CrudRepository<AuthRoles, AuthRolesPK>{

}
