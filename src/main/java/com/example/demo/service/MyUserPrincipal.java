package com.example.demo.service;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.model.User;

public class MyUserPrincipal implements UserDetails {

	private User user;

	public MyUserPrincipal(User user) {

		super();
		this.user = user;
	}

	public User getUser() {

		return user;
	}

	public void setUser(User user) {

		this.user = user;
	}

	@Override
	public String toString() {

		return "MyUserPrincipal [user=" + user + "]";
	}

	public boolean hasRole(String roleName) {

		return this.user.hasRole(roleName);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
	}

	@Override
	public String getPassword() {

		return user.getPassword();
	}

	@Override
	public String getUsername() {

		return user.getUsername();
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
