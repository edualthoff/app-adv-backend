package br.app.adv.main.person.client;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientUserDAO extends CrudRepository<ClientUser, Long>{

	ClientUser  findByPessoaId(long id);
}
