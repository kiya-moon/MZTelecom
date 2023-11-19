package com.team.mztelecom.dto;

import com.team.mztelecom.domain.CustBas;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * 고객 정보 저장용 DTO
 * 
 * @author USER
 *
 */

@Getter
@NoArgsConstructor
public class CustBasSaveDTO {
	
	String custId;				// 고객아이디
	String custNm;				// 고객명
	String custPassword;		// 고객패스워드
	String custIdfyNo;			// 고객식별번호
	String custBirth;			// 생년월일(yyyy.mm.dd)
	String custNo;				// 고객번호(전화번호)
	String custSex;				// 고객성별
	String custAddress;			// 고객주소
	String custEmail;			// 고객이메일
	
	@Builder
	public CustBasSaveDTO(String custId, String custNm, String custPassword, String custIdfyNo, String custBirth, String custNo, String custSex, String custAddress, String custEmail) {
		this.custId = custId;
		this.custNm = custNm;
		this.custPassword = custPassword;
		this.custIdfyNo = custIdfyNo;
		this.custBirth = custBirth;
		this.custNo = custNo;
		this.custSex = custSex; 
		this.custAddress = custAddress;
		this.custEmail = custEmail;
	}
	
	// dto -> entity
	public CustBas toEntity() {
		return CustBas.builder()
				.custId(custId)
				.custNm(custNm)
				.custPassword(custPassword)
				.custIdfyNo(custIdfyNo)
				.custBirth(custBirth)
				.custNo(custNo)
				.custSex(custSex)
				.custAddress(custAddress)
				.custEmail(custEmail)
				.build();
	}
}
