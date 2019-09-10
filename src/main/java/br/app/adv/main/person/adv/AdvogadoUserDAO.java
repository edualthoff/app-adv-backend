package br.app.adv.main.person.adv;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvogadoUserDAO extends CrudRepository<AdvogadoUser, Long> {

	AdvogadoUser findByPessoaId(BigInteger id);

}
