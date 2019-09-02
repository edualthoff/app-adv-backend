package br.app.adv.main.upload;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadFileDAO extends CrudRepository<UploadFile, Long> {

	UploadFile findById(long id);
}
