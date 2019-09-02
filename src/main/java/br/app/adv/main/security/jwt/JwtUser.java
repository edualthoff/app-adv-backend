package br.app.adv.main.security.jwt;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUser  implements UserDetails {
	private static final long serialVersionUID = 160501951776060243L;
	
	private long id;
	private String username;
	private String password;
	private boolean verificado;
	private String session;
	private Collection<? extends GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	
	public JwtUser(long id, boolean verificado, String username, String session,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.verificado = verificado;
		this.session = session;
		this.authorities = authorities;
	}
	
	public JwtUser(long id, String username, String password, boolean verificado , Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.verificado = verificado;
		this.authorities = authorities;
	}

	@JsonIgnore
	public long getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	@JsonIgnore
	public boolean getVerificado() {
		return verificado;
	}
	@JsonIgnore
	public String getSession() {
		return session;
	}	
}
