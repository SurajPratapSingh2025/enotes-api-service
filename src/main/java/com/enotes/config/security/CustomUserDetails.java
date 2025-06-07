package com.enotes.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.enotes.entity.User;

public class CustomUserDetails implements UserDetails{
	
	private User user;

	public CustomUserDetails(User user2) {
		super();
		this.user=user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<SimpleGrantedAuthority> authority=new ArrayList<>();
		user.getRoles().forEach(r->{
			authority.add(new SimpleGrantedAuthority(r.getName()));
		});
		
		return null;
	}

	@Override
	public String getPassword() {
		
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		
		return user.getEmail();
	}

	public Object getUser() {
		
		return user;
	}

}
