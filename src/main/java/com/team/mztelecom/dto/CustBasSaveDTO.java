package com.team.mztelecom.dto;

import com.team.mztelecom.domain.CustBas;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
public class CustBasSaveDTO {
	
//	@NotEmpty(message = "아이디를 입력해 주세요.")
	String custId;				// 고객아이디
	
//	@NotEmpty(message = "이름을 입력해 주세요.")
	String custNm;				// 고객명
	
//	@NotEmpty(message = "비밀번호를 입력해 주세요.")
	String custPassword;		// 고객패스워드
	
//	@NotEmpty(message = "비밀번호 확인을 입력해 주세요.")
	String custPasswordCheck;		// 고객패스워드 확인	
	
//	@NotEmpty(message = "주민번호를 입력해 주세요.")
	String custIdfyNo;			// 고객식별번호
	
	String custBirth;			// 생년월일(yyyy.mm.dd), 서비스단에서 처리
	
//	@NotEmpty(message = "핸드폰번호를 입력해 주세요.")
	String custNo;				// 고객번호(전화번호)
	
	String custSex;				// 고객성별, 서비스단에서 처리
	
//	@NotEmpty(message = "주소를 입력해 주세요.")
	String custAddress;			// 고객주소
	
	@Email
//	@NotEmpty(message = "이메일을 입력해 주세요.")
	String custEmail;			// 고객이메일
	
	@Builder
	public CustBasSaveDTO(String custId, String custNm, String custPassword, String custPasswordCheck, String custIdfyNo, String custBirth, String custNo, String custSex, String custAddress, String custEmail) {
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
