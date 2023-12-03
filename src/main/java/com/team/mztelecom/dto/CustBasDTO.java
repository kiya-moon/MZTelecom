package com.team.mztelecom.dto;

import java.util.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.team.mztelecom.domain.CustBas;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustBasDTO implements UserDetails {
	String custId;				// 고객아이디
	String custNm;				// 고객명
	String custPassword;		// 고객패스워드
	String custIdfyNo;			// 고객식별번호
	String custNo;				// 고객번호(전화번호)
	String custEmail;			// 고객이메일
	
	private CustBas custBas;

	public CustBasDTO(CustBas custBas) {
		this.custBas = custBas;
	}

	
	@Override
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
