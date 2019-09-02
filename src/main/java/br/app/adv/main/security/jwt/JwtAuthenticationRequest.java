package br.app.adv.main.security.jwt;

import java.io.Serializable;

/*
 * Classe responsavel por coletar as informações de autenticação da URL de login 
 */
public class JwtAuthenticationRequest implements Serializable {

	private static final long serialVersionUID = 7720914914408375433L;
	
	private String email;
	private String password;

	public JwtAuthenticationRequest() {
		super();
	}

	public JwtAuthenticationRequest(String email, String password) {
		this.setEmail(email);
		this.setPassword(password);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
