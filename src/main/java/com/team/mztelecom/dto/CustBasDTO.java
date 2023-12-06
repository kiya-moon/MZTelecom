package com.team.mztelecom.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustBasDTO {
	String custId;				// 고객아이디
	String custNm;				// 고객명
	String custPassword;		// 고객패스워드
	String custIdfyNo;			// 고객식별번호
	String custNo;				// 고객번호(전화번호)
	String custEmail;			// 고객이메일
	
}
