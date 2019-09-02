package br.app.adv.main.security.mediasocial;

public class SocialMediaModel {

	private Long id;
	private String email;
	private String nome;
	private String primeiroNome;
	private String ultimoNome;
	private String urlImagem;
	private String niverData;
	private String mediaSocial;
	
	public SocialMediaModel() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUltimoNome() {
		return ultimoNome;
	}

	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public String getNiverData() {
		return niverData;
	}

	public void setNiverData(String niverData) {
		this.niverData = niverData;
	}

	public String getMediaSocial() {
		return mediaSocial;
	}

	public void setMediaSocial(String mediaSocial) {
		this.mediaSocial = mediaSocial;
	}
}
