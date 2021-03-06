package com.whitrus.spring.tester.security;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.whitrus.spring.tester.domain.user.model.UserRole;
import com.whitrus.spring.tester.domain.user.model.UserRoleConverter;

public class UserAuthenticationDTO implements UserDetails {

	private static final long serialVersionUID = 1L;

	private static final UserRoleConverter roleConverter = new UserRoleConverter();

	private final String login;
	private final String password;
	private final List<GrantedAuthority> authorities;

	public UserAuthenticationDTO(String login, String password, String rolesAsText) {
		this.login = login;
		this.password = password;

		Set<UserRole> roles = roleConverter.convertToEntityAttribute(rolesAsText);

		for (UserRole role : roles) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
			authorities.add(authority);
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return login;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
