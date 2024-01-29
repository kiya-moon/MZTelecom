package com.team.mztelecom.dto;

import java.time.LocalDateTime;

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
	String custId;
	String custNm;
	String custBirth;
	String custNo;
	String custSex;
	String custAddress;
	String custEmail;
	String intmPurStusYn;
	LocalDateTime createDate;

	@Builder
	public InquiryCustDTO(String custId, String custNm,String custBirth, String custNo, String custSex, 
			String custAddress, String custEmail, String intmPurStusYn, LocalDateTime createDate) {
		this.custId = custId;
		this.custNm = custNm;
		this.custBirth = custBirth;
		this.custNo = custNo;
		this.custSex = custSex;
		this.custAddress = custAddress;
		this.custEmail = custEmail;
		this.intmPurStusYn = intmPurStusYn;
		this.createDate = createDate;
	}

	public CustBas toEntity() {
		return CustBas.builder().custId(custId).custNm(custNm).custBirth(custBirth).custNo(custNo).custSex(custSex)
				.custAddress(custAddress).custEmail(custEmail).intmPurStusYn(intmPurStusYn).createDate(createDate).build();
	}
}
