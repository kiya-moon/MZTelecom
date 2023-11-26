package com.team.mztelecom.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PrincipalDetails implements UserDetails {
	
	private CustBas custBas;
	private boolean isAdmin;
	
	public PrincipalDetails(CustBas custBas) {
		this.custBas = custBas;
	}

	// 사용자가 가지고 있는 권한 목록 반환
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();

        // 사용자가 관리자인 경우 "ADMIN" 권한을 추가
        if (isAdmin) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }

        // 일반 사용자인 경우 "USER" 권한을 추가
        authorities.add(new SimpleGrantedAuthority("USER"));

        return authorities;
	}

	// 사용자 비밀번호 반환, 암호화하여 저장해야 함
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return custBas.getCustPassword();
	}

	// 사용자 식별 가능한 이름 반환, 고유한 이름이어야 함
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return custBas.getCustId();
	}

	// 계정 만료 확인, 만료되지 않은 값: true, 만료된 값: false
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정 잠금 확인, 잠금되지 않은 값: true, 잠금된 값: false
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	// 비밀번호 만료 확인, 만료되지 않은 값: true, 만료된 값 : false
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정 사용 확인, 사용 가능: true, 사용 불가능: false
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
