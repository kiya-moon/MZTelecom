package com.team.mztelecom.dto;

import java.time.LocalDateTime;

import com.team.mztelecom.domain.CustBas;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * 고객 정보 저장용 DTO
 * 
 * @author USER
 *
 */

@Getter
@Setter
@NoArgsConstructor
public class CustBasDTO {
	
	Long id;
	String custId;					// 고객아이디
	String custNm;					// 고객명
	String custPassword;			// 고객패스워드
	String custPasswordCheck;		// 고객패스워드 확인	
	String custIdfyNo;				// 고객식별번호
	String custBirth;				// 생년월일(yyyy.mm.dd)
	String custNo;					// 고객번호(전화번호)
	String custSex;					// 고객성별
	String custAddress;				// 고객주소 : 회원가입 시에는 들어오지 않음
	String custEmail;				// 고객이메일
	String emailDomain;				// 이메일도메인
	String intmPurStusYn;			// 기기구매여부
	LocalDateTime createDate;	// 가입

	@Builder
	public CustBasDTO(Long id, String custId, String custNm, String custPassword, String custPasswordCheck, String custIdfyNo, 
			String custBirth, String custNo, String custSex, String custAddress, String custEmail, String emailDomain, 
			String intmPurStusYn, LocalDateTime createDate ) {

		this.id = id;
		this.custId = custId;
		this.custNm = custNm;
		this.custPassword = custPassword;
		this.custPasswordCheck = custPasswordCheck;
		this.custIdfyNo = custIdfyNo;
		this.custBirth = custBirth;
		this.custNo = custNo;
		this.custSex = custSex;
		this.custAddress = custAddress;
		this.custEmail = custEmail;
		this.emailDomain = emailDomain;
		this.intmPurStusYn = intmPurStusYn;
		this.createDate = createDate;
	}
	
	// dto -> entity
	public CustBas toEntity() {
		return CustBas.builder()
				.id(id)
				.custId(custId)
				.custNm(custNm)
				.custPassword(custPassword)
				.custIdfyNo(custIdfyNo)
				.custBirth(custBirth)
				.custNo(custNo)
				.custSex(custSex)
				.custAddress(custAddress)
				.custEmail(custEmail)
				.intmPurStusYn(intmPurStusYn)
				.createDate(createDate)
				.build();
	}

}
