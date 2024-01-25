package com.team.mztelecom.dto;

import com.team.mztelecom.domain.IntmProduct;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IntmProductDTO {
	Long   id;				// id
	String intmModelId;	// 대표기기모델아이디
	String intmSeq;			// 기기일련번호
	String intmIdfyNo;		// 기기식별번호
	String intmSalesStatus;	// 기기판매여부
	
	@Builder
	public IntmProductDTO(Long id, String intmModelId, String intmSeq, String intmIdfyNo,
			String intmSalesStatus) {

		this.id = id;
		this.intmModelId = intmModelId;
		this.intmSeq = intmSeq;
		this.intmIdfyNo = intmIdfyNo;
		this.intmSalesStatus = intmSalesStatus;
	}
	
	public IntmProduct toEntity() {
		return IntmProduct.builder()
				.id(id)
				.intmModelId(intmModelId)
				.intmSeq(intmSeq)
				.intmIdfyNo(intmIdfyNo)
				.intmSalesStatus(intmSalesStatus)
				.build();
	}


}

