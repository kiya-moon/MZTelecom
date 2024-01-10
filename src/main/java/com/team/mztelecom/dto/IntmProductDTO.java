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
	String repIntmModelId;	// 대표기기모델아이디
	String intmSeq;			// 기기일련번호
	String intmIdfyNo;		// 기기식별번호
	String intmSalesStatus;	// 기기판매여부
	String intmBuyerId;		// 기기구매자아이디
	
	@Builder
	public IntmProductDTO(Long id, String repIntmModelId, String intmSeq, String intmIdfyNo,
			String intmSalesStatus, String intmBuyerId) {

		this.id = id;
		this.repIntmModelId = repIntmModelId;
		this.intmSeq = intmSeq;
		this.intmIdfyNo = intmIdfyNo;
		this.intmSalesStatus = intmSalesStatus;
		this.intmBuyerId = intmBuyerId;
	}
	
	public IntmProduct toEntity() {
		
		return IntmProduct.builder()
				.id(id)
				.repIntmModelId(repIntmModelId)
				.intmSeq(intmSeq)
				.intmIdfyNo(intmIdfyNo)
				.intmSalesStatus(intmSalesStatus)
				.intmBuyerId(intmBuyerId)
				.build();
	}


}

