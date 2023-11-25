package com.team.mztelecom.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class CustBas {

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
	
	private String role;				// 권한
	
	
	// DTO <-> Entity
	@Builder
	public CustBas(String custId, String custNm, String custPassword, String custIdfyNo, String custNo, String custEmail, String custBirth, String custAddress, String custSex, String intmPurStusYn) {
		
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
}
