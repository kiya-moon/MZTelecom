package com.team.mztelecom.domain;

import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CustBas implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Column(unique = true)
	private String custId;				// 고객아이디
	
	@NotNull
	private String custNm;				// 고객명
	
	private String custPassword;		// 고객패스워드
	
	@NotNull
	@Column(unique = true)
	private String custIdfyNo;			// 고객식별번호(주민번호)
	
	private String custBirth;			// 생년월일(yyyy.mm.dd)
	
	@NotNull
	private String custNo;				// 고객번호(전화번호)
	
	private String custSex;				// 고객성별
	
	private String custAddress;			// 고객주소
	
	@NotNull
	@Column(unique = true)
	private String custEmail;			// 고객이메일
	
	private String intmPurStusYn;		// 기기구매여부
	
	// DTO <-> Entity
	@Builder
	public CustBas( String custId, String custNm, String custPassword, String custIdfyNo, String custNo, String custEmail, String custBirth, String custAddress, String custSex, String intmPurStusYn) {
		
		this.custId = custId;
		this.custNm = custNm;
		this.custPassword = custPassword;
		this.custIdfyNo = custIdfyNo;
		this.custNo = custNo;
		this.custEmail = custEmail;
		this.custBirth = custBirth;
		this.custAddress = custAddress;
		this.custSex = custSex;
		this.intmPurStusYn = intmPurStusYn;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();

        // 모든 회원은 user 권한을 가짐
        collection.add(new SimpleGrantedAuthority("ROLE_USER"));

        // Id가 ROLE_ADMIN인 회원은 admin 권한도 가짐
        if (isAdmin()) {
        	collection.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return collection;
    }
	
	public boolean isAdmin() {
        return "admin".equals(custId);
    }


	@Override
	public String getPassword() {
		return getCustPassword();
	}

	@Override
	public String getUsername() {
		return getCustId();
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
