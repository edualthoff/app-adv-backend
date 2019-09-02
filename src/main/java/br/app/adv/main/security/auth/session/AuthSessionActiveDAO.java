package br.app.adv.main.security.auth.session;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuthSessionActiveDAO extends CrudRepository<AuthSessionActive, Long> {

	List<AuthSessionActive> findAllByPersonId(Long id);
}
