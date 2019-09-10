package br.app.adv.main.security.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.app.adv.main.exception.sql.NotEmptySqlException;
import br.app.adv.main.exception.GenericErrorException;
import br.app.adv.main.exception.sql.InvalidValueSqlException;
import br.app.adv.main.person.Person;
import br.app.adv.main.person.PersonTypeEnum;
import br.app.adv.main.security.account.AccountCadastro;
import br.app.adv.main.security.account.AccountLogin;
import br.app.adv.main.security.account.AccountMain;
import br.app.adv.main.security.auth.IUserAuthentication;
import br.app.adv.main.security.auth.session.AuthSessionActive;
import br.app.adv.main.security.jwt.JwtAuthenticationRequest;
import br.app.adv.main.security.jwt.JwtTokenUtil;
import br.app.adv.main.security.jwt.JwtUser;
import br.app.adv.main.security.model.CurrentUser;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * Classes Controller (GET, POST, etc) do acesso ao sistema
 */
@RestController
@CrossOrigin(origins = "*")
public class AuthenticationRestController {
	private static final Logger log = LogManager.getLogger(AuthenticationRestController.class);
	
	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private AccountLogin accountLogin;
	@Autowired private AccountCadastro accCadastro;
	@Autowired private JwtTokenUtil jwtTokenUtil;
	@Autowired private IUserAuthentication userAuth;
	@Autowired private AccountMain accountMain;
	
	/**
	 * @param String email
	 * @param String password
	 * @param Int session / ROLE_CLIENT(1), ROLE_ADV(2), ROLE_ASSOCIADO(3)
	 * @throws AuthenticationException
	 */
	@PostMapping(value = "/api/auth")
	public ResponseEntity<?> createAuthenticationToken(HttpServletRequest request,
			@RequestBody JwtAuthenticationRequest authenticationRequest, 
			@RequestParam("mobileid") String mobileid, @RequestParam("modelo") String modelo) throws AuthenticationException {
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
						authenticationRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		AuthSessionActive session = new AuthSessionActive(mobileid, modelo);
		final CurrentUser currentUser = accountMain.gerarJWT(authenticationRequest.getEmail(), session);
		
		return ResponseEntity.ok(currentUser);
	}
	/*
	 * Rest Controller de acesso e cadastro das Midias Sociais -- Facebook, Google
	*/
	@PostMapping(value = "/api/auth/mediasocial")
	public ResponseEntity<?> getFacebookAcess(@RequestBody Map<String, String> json,
			@RequestParam("mobileid") String mobileid, @RequestParam("modelo") String modelo) throws GenericErrorException, IOException {
		String mediaSocial = json.get("mediaSocial");
		String token = json.get("token");
		log.debug("Loggin MediaSocial {} token {} ", mediaSocial, token);
		final CurrentUser currentUser;
			String email = this.accountLogin.loginMediaSocial(mediaSocial, token);
			AuthSessionActive session = new AuthSessionActive(mobileid, modelo);
			currentUser = accountMain.gerarJWT(email, session);
		
		return ResponseEntity.ok(currentUser);
	}
	
	@PutMapping(value = "/api/auth/cadastrar/{tipoUser}")
	public ResponseEntity<?> cadastrarPessoa(HttpServletRequest request,@RequestBody @Valid Person pessoa, @Valid @PathVariable("tipoUser") String tipoUser, 
			@Valid @RequestParam("mobileid") String mobileid, @Valid @RequestParam("modelo") String modelo) 
			throws InvalidValueSqlException, NotEmptySqlException {
		final CurrentUser currentUser;
		JwtUser jwt = userAuth.getJwtUserAuthentication();
		String email;
		if(jwt != null) {
			email = this.accCadastro.validarPersonMediaSocial(pessoa, PersonTypeEnum.getPerson(tipoUser), jwt.getUsername());
		}else {
			email = this.accCadastro.validarPersonSenha(pessoa, PersonTypeEnum.getPerson(tipoUser));
		}
		AuthSessionActive session = new AuthSessionActive(mobileid, modelo);
		currentUser = accountMain.gerarJWT(email, session);
		return ResponseEntity.ok(currentUser);
	}
	
	@PostMapping(value = "/api/refresh")
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		//String username = jwtTokenUtil.getUsernameFromToken(token);

		if (jwtTokenUtil.canTokenBeRefreshed(token)) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new CurrentUser(refreshedToken));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}
}
