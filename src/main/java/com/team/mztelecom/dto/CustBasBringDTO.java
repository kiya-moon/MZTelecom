package com.team.mztelecom.dto;

import com.team.mztelecom.domain.CustBas;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * 회원정보페이지용 고객 정보 DTO
 * 
 * @author USER
 *
 */

@Getter
@NoArgsConstructor
public class CustBasBringDTO {

	String custId; // 고객아이디
	String custNm; // 고객명
	String custPassword; // 고객패스워드
	String custNo; // 고객번호(전화번호)
	String custAddress; // 고객주소
	String custEmail; // 고객이메일
	String intmPurStusYn; // 기기구매여부
	
	// entity -> dto
	public CustBasBringDTO(CustBas entity) {
		this.custId = entity.getCustId();
		this.custNm = entity.getCustNm();
		this.custPassword = entity.getCustPassword();
		this.custNo = entity.getCustNo();
		this.custAddress = entity.getCustAddress();
		this.custEmail = entity.getCustEmail();
		this.intmPurStusYn = entity.getIntmPurStusYn();
	}

}
