package br.app.adv.main.security.auth.mediasocial;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthMediaSocialDAO extends CrudRepository<AuthMediaSocial, Long>{

	AuthMediaSocial findByUserIdAndTipo(long id, String tipo);
}
