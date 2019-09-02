package br.app.adv.main.security.account;

import java.io.IOException;
import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.app.adv.main.exception.GenericErrorException;
import br.app.adv.main.person.Person;
import br.app.adv.main.person.PersonService;
import br.app.adv.main.security.auth.mediasocial.AuthMediaSocial;
import br.app.adv.main.security.auth.mediasocial.AuthMediaSocialDAO;
import br.app.adv.main.security.mediasocial.SocialMediaModel;
import br.app.adv.main.security.mediasocial.SocialMediaValidate;
import br.app.adv.main.upload.FileStorage;

/**
 * Valida as informações do usuario e gera o login .. * 
 * @author Eduardo
 */
@Service
public class AccountLogin implements Serializable {
	private static final long serialVersionUID = -7812489002490963148L;

	private static final Logger logger = LogManager.getLogger(AccountLogin.class);

	@Autowired
	private PersonService personService;
	// @Autowired private AccountMain accountMain;
	@Autowired
	private AuthMediaSocialDAO authMSDAO;

	public AccountLogin() {}

	/*
	 * Apos validar o usuario no BD monta o token com as informaçoes do usuario e
	 * gera o currentUSer -- class responsavel por retornar as info pro FrontEnd
	 */
	public String loginUsername(String email) {
		logger.info(
				"Implemtna o CurrentUser e gera o Token, login realizado com o User e Password.. Email do Usuario {}",
				email);
		if ((email != null)) {
			return email;
		}
		return email;
	}

	/*
	 * Metodo que valida o usuario que realizar o login pelas Redes Sociais
	 */
	public String loginMediaSocial(String mediaSocial, String tokem) throws IOException, GenericErrorException {
		AuthMediaSocial authMS = new AuthMediaSocial();
		if (tokem != null) {
			SocialMediaValidate infoUserSocial = new SocialMediaValidate(tokem);
			SocialMediaModel socialM = new SocialMediaModel();
			socialM = infoUserSocial.acessMediaSocial(mediaSocial);
			logger.info("AccountMain - loggin().. Email do Usuario {}", socialM.getEmail());
			// logger.debug("Id Usuario loginMediaSocial {} e {} ",model, user.getId());
			authMS = this.authMSDAO.findByUserIdAndTipo(authMS.getUserId(), authMS.getTipo());

			if (authMS == null) {
				Person user = new Person();
				user = personService.getConsultaEmail(socialM.getEmail());
				if (user == null) {
					logger.info("Person não existe no BD - retorna nulo");
					final Person p = new Person();
					final FileStorage fileS = new FileStorage();
					// p.setPrimeiroNome(socialM.getPrimeiroNome());
					// p.setUltimoNome(socialM.getUltimoNome());
					p.setNomeCompleto(socialM.getNome());
					p.setEmail(socialM.getEmail());
					p.setImagemPerfil(fileS.baixarArquivoUrl("jpg", "photo.jpg", socialM.getUrlImagem()));
					// p.setData_nasc(new
					// SimpleDateFormat("MM/dd/yyyy").parse(socialM.getNiverData()));
					this.personService.setSalvarPessoa(p);
					final AuthMediaSocial authMS2 = new AuthMediaSocial();
					authMS2.setTipo(mediaSocial);
					authMS2.setPessoa(p);
					authMS2.setUserId(socialM.getId());
					this.authMSDAO.save(authMS);

					return p.getEmail();
				}
			}
		}
		return authMS.getPessoa().getEmail();
	}
}
