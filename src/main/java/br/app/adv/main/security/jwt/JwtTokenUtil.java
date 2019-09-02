package br.app.adv.main.security.jwt;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenUtil implements Serializable {
	private static final long serialVersionUID = -3915217797039862867L;

	private static final Logger log = LogManager.getLogger(JwtTokenUtil.class);

	static final String CLAIM_KEY_ID = "sub";
	static final String CLAIM_KEY_SESSION = "jti";
	static final String CLAIM_KEY_PASS = "pass";
	static final String CLAIM_KEY_USERNAME = "email";
	static final String CLAIM_KEY_VERIFIED = "verified";
	static final String CLAIM_KEY_CREATED = "created";
	static final String CLAIM_KEY_EXPIRED = "exp";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

	// Separa o nome do usuario (Username) do token. @Param token
	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	// Separa a data que vai expirar o token (Expiration Date) do token. @Param token
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	// metodo que pegar o statusToken do token para ser validado
	public int getStatusFromToken(String token) {
		Integer tokenstatus;
		try {
			final Claims claims = getClaimsFromToken(token);
			tokenstatus = claims.get("statusToken", Integer.class);
		} catch (Exception e) {
			tokenstatus = null;
		}

		return tokenstatus;
	}

	private Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(UserDetails userDetails, long mobileId) {
		Map<String, Object> claims = new HashMap<>();
		
		claims.put(CLAIM_KEY_ID, ((JwtUser) userDetails).getId());
		log.debug("Id Usuario / Person generateToken, {}", ((JwtUser) userDetails).getId());
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_PASS, userDetails.getAuthorities());
		claims.put(CLAIM_KEY_VERIFIED, ((JwtUser) userDetails).getVerificado());
		claims.put(CLAIM_KEY_SESSION, mobileId);
		
		final Date createdDate = new Date();
		claims.put(CLAIM_KEY_CREATED, createdDate);

		return doGenerateToken(claims);
	}

	private String doGenerateToken(Map<String, Object> claims) {
		final Date createdDate = (Date) claims.get(CLAIM_KEY_CREATED);
		final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
		return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}

	public Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token));
	}

	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, new Date());
			refreshedToken = doGenerateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	// Metodo que validade o token de acordo a regra de negocio
	public Authentication getAuthentication(String token) throws JsonParseException, JsonMappingException, IOException {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(CLAIM_KEY_PASS).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        
        System.out.println(claims.get(CLAIM_KEY_PASS).toString());
		JwtUser user = new JwtUser (
        		Long.parseLong(claims.getSubject()),
        		Boolean.parseBoolean(claims.get(CLAIM_KEY_VERIFIED).toString()),
        		claims.get(CLAIM_KEY_USERNAME).toString(),
        		claims.get(CLAIM_KEY_SESSION).toString(),
        		authorities
        		);

		return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			log.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}
}
	