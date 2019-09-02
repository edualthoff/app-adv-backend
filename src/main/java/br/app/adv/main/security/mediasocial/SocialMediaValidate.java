package br.app.adv.main.security.mediasocial;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.app.adv.main.exception.GenericErrorException;
import br.app.adv.main.exception.MessageCodeExceptionEnum;

/*
 * Class responsavel por validar e trazer as informaçoes do Usuario do Login por Redes Sociais
 */
public class SocialMediaValidate implements Serializable {
	private static final long serialVersionUID = -2075478866593319001L;

	private static final Logger logger = LogManager.getLogger(SocialMediaValidate.class);
	
	private String accessToken;
	private SocialMediaModel socialMedia;
	
	public SocialMediaValidate(String accessToken) {
		this.accessToken = accessToken;
	}	
	
	public SocialMediaModel acessMediaSocial(String mediaSocial) throws GenericErrorException {
		
		switch (mediaSocial) {
		case "facebook":
			this.socialMedia = this.facebookAcess();
			logger.debug("acessMediaSocial Facebook, {}", socialMedia.getEmail());
			break;
		case "google":
			this.socialMedia = this.googleAcess();
			logger.debug("acessMediaSocial Google, {}", socialMedia.getEmail());
			break;
		}
		return this.socialMedia;
	}
	/*
	 * Monta o RestTemplate para comunicar com a API da rede social informada e gera o JSON com as Info solicitada pela URI
	 */
	private JsonNode consultaSocialMedia(String mediaSocial, UriComponentsBuilder uri) throws GenericErrorException  {
		logger.debug("RestTemplate monta e retorna o Json da url da midia social, {}", mediaSocial);
	    RestTemplate restTemplate = new RestTemplate();
	    try {
	    	HttpHeaders headers = new HttpHeaders();
	        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	        HttpEntity<?> entity = new HttpEntity<>(headers);
	        
	        ResponseEntity<String> response = restTemplate.exchange(
	        		uri.toUriString(), 
	        		HttpMethod.GET, entity, String.class);
	        ObjectMapper json = new ObjectMapper();
	      return json.readTree(response.getBody()); 

	    } catch (Exception exp) {
	      	logger.error("User is not authorized to login into system", exp);
	       throw new GenericErrorException(MessageCodeExceptionEnum.GENERIC_ERROR.getIdErro(), MessageCodeExceptionEnum.GENERIC_ERROR.getMessageErro(), "Error ao buscar as informações do usuario.");
	    }
	}
	/*
	 * Monta a URI da rede social informada e gera o @param SocialMediaModel -- com as info do usuario
	 */
	private SocialMediaModel facebookAcess() throws GenericErrorException {
		logger.debug("Calling Facebook API to validate and get profile info");
   
	    // field names which will be retrieved from facebook
	    final String fields = "id,email,first_name,last_name,picture,birthday";
	  
	    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://graph.facebook.com/v3.3/me")
	        		.queryParam("access_token", this.accessToken).queryParam("fields", fields);
	    logger.debug("Facebook profile uri {}", uriBuilder.toUriString());
	    
	    JsonNode json = this.consultaSocialMedia("Facebook", uriBuilder);
	    
	    SocialMediaModel socialM = new SocialMediaModel();
	    socialM.setId(json.get("id").asLong());
	    socialM.setEmail(json.get("email").asText());
	    socialM.setNome(json.get("first_name").asText()+" "+json.get("last_name").asText());
	    socialM.setPrimeiroNome(json.get("first_name").asText());
	    socialM.setUltimoNome(json.get("last_name").asText());
	    socialM.setUrlImagem(json.get("picture").get("data").get("url").asText());
	    socialM.setNiverData(json.get("birthday").asText());
	    
	    return socialM;
	}
	/*
	* Monta a URI da rede social informada e gera o @param SocialMediaModel -- com as info do usuario
	*/
	private SocialMediaModel googleAcess() throws GenericErrorException {
		logger.debug("Calling Google API to get token info");
		
		UriComponentsBuilder uriBuilder = UriComponentsBuilder
            		.fromUriString("https://www.googleapis.com/oauth2/v3/userinfo").queryParam("access_token", accessToken);
        logger.debug("google login uri {}", uriBuilder.toUriString());
        
	    JsonNode json = this.consultaSocialMedia("Google", uriBuilder);
	    
	    SocialMediaModel socialM = new SocialMediaModel();
	    socialM.setId(json.get("sub").asLong());
	    socialM.setEmail(json.get("email").asText());
	    socialM.setNome(json.get("name").asText());
	    socialM.setPrimeiroNome(json.get("given_name").asText());
	    socialM.setUltimoNome(json.get("family_name").asText());
	    socialM.setUrlImagem(json.get("picture").asText());
	    
        return socialM;
    }
}
