package br.app.adv.main.security.model;

import org.springframework.stereotype.Component;

/*
 * Classe responsavel por gerar e retornar os dados de acesso e o token do usuario apos o login 
 * Tem a regra de negocio para gerar o log da sessão e para adicionar a data source do tenant que realizou o login
 */
@Component
public class CurrentUser {

	private String token;

	public CurrentUser() {
		super();
	}

	public CurrentUser( String token ) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}