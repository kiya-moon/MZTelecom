package com.team.mztelecom.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateTimeConverter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CustBas implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Column(unique = true)
	private String custId;					// 고객아이디
	
	@NotNull
	private String custNm;					// 고객명
	
	private String custPassword;			// 고객패스워드
	
	@NotNull
	@Column(unique = true)
	private String custIdfyNo;				// 고객식별번호(주민번호)
	
	private String custBirth;				// 생년월일(yyyy.mm.dd)
	
	@NotNull
	private String custNo;					// 고객번호(전화번호)
	
	private String custSex;					// 고객성별
	
	private String custAddress;				// 고객주소
	
	@NotNull
	@Column(unique = true)
	private String custEmail;				// 고객이메일
	
	private String intmPurStusYn;			// 기기구매여부
	
	@CreatedDate
	@Column(updatable = false, nullable = false)
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime createDate;	// 가입
	
	// DTO <-> Entity
	@Builder
	public CustBas( String custId, String custNm, String custPassword, String custIdfyNo, String custNo, String custEmail, 
			String custBirth, String custAddress, String custSex, String intmPurStusYn, LocalDateTime createDate) {
		
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
		this.createDate = createDate;
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
	
	// 관리자 권한
	public boolean isAdmin() {
        return "admin".equals(custId);
    }

	
	// 사용자 id 반환
	@Override
	public String getUsername() {
		return getCustId();
	}
	
	// 사용자 비밀번호 반환
	@Override
	public String getPassword() {
		return getCustPassword();
	}
	
	// 계정 만료 여부, true: 만료X
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	
	// 계정 잠금 여부, true: 잠금X
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 패스워드 만료 여부, true: 만료X
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정 사용 가능 여부, true: 사용가능
	@Override
	public boolean isEnabled() {
		return true;
	}

}
