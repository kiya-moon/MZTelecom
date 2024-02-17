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
	Long id;
	String custId;
	String custNm;
	String custBirth;
	String custNo;
	String custSex;
	String custAddress;
	String custEmail;
	LocalDateTime createDate;
	
	@Builder
	public InquiryCustDTO(Long id, String custId, String custNm,String custBirth, String custNo, String custSex, 
			String custAddress, String custEmail, LocalDateTime createDate) {
		this.id = id;
		this.custId = custId;
		this.custNm = custNm;
		this.custBirth = custBirth;
		this.custNo = custNo;
		this.custSex = custSex;
		this.custAddress = custAddress;
		this.custEmail = custEmail;
		this.createDate = createDate;
	}

	public CustBas toEntity() {
		return CustBas.builder().id(id).custId(custId).custNm(custNm).custBirth(custBirth).custNo(custNo).custSex(custSex)
				.custAddress(custAddress).custEmail(custEmail).createDate(createDate).build();
	}
}
