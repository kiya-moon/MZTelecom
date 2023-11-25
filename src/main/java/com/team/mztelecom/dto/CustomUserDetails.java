package com.team.mztelecom.dto;

import java.util.*;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.team.mztelecom.domain.CustBas;

public class CustomUserDetails implements UserDetails { // OAuth2User 
	
	private CustBas custBas;

	public CustomUserDetails(CustBas custBas) {
		this.custBas = custBas;
	}
	
//	public CustomUserDetails(CustBas custBas, Map<String, Object> attributes) {
//		this.custBas = custBas;
//		this.attributes = attributes;
//	}

	@Override // 사용자의 특정한 권한을 주는 메서드
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collection = new ArrayList<>();
		
		collection.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				
				return custBas.getRole();
			}
		});
			
		
		return collection;
	}

	@Override
	public String getPassword() {
		return custBas.getCustPassword();
	}

	@Override
	public String getUsername() {
		return custBas.getCustId();
	}
	
	// 계정이 만료 되었는지 (true: 만료X)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겼는지 (true: 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	// 비밀번호가 만료되었는지 (true: 만료X)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// 계정이 활성화 상태인지 (true: 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// OAuth2User
//	@Override
//	public Map<String, Object> getAttributes() {
//		// TODO Auto-generated method stub
//		return attributes;
//	}

}
