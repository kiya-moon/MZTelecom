package com.team.mztelecom.dto;

import com.team.mztelecom.domain.CustBas;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * 고객 정보 조회용 DTO
 * 
 * @author ADMIN
 *
 */

@Getter
@Setter
@NoArgsConstructor
public class InquiryCustDTO {
	String custId; // 고객아이디
	String custNm; // 고객명
	String custIdfyNo; // 고객식별번호
	String custBirth; // 생년월일(yyyy.mm.dd)
	String custNo; // 고객번호(전화번호)
	String custSex; // 고객성별
	String custAddress; // 고객주소 : 회원가입 시에는 들어오지 않음
	String custEmail; // 고객이메일
	String intmPurStusYn; // 기기구매여부

	@Builder
	public InquiryCustDTO(String custId, String custNm,String custIdfyNo,String custBirth, 
			String custNo, String custSex, String custAddress, String custEmail, String intmPurStusYn) {
		this.custId = custId;
		this.custNm = custNm;
		this.custIdfyNo = custIdfyNo;
		this.custBirth = custBirth;
		this.custNo = custNo;
		this.custSex = custSex;
		this.custAddress = custAddress;
		this.custEmail = custEmail;
		this.intmPurStusYn = intmPurStusYn;
	}

	public CustBas toEntity() {
		return CustBas.builder().custId(custId).custNm(custNm).custIdfyNo(custIdfyNo)
				.custBirth(custBirth).custNo(custNo).custSex(custSex).custAddress(custAddress)
				.custEmail(custEmail).intmPurStusYn(intmPurStusYn).build();
	}
}
