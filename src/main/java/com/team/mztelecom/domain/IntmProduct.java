package com.team.mztelecom.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class IntmProduct {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotNull
    private String repIntmModelId;		// 대표기기모델아이디

	private String intmSeq; 			// 기기일련번호

	@NotNull
    @Column(unique = true)
    private String intmIdfyNo;			// 기기식별번호

	private String intmSalesStatus; 	// 기기판매여부

    private String intmBuyerId;			// 기기구매자아이디

    @Builder
	public IntmProduct(Long id, String repIntmModelId, String intmSeq, String intmIdfyNo,
			String intmSalesStatus, String intmBuyerId) {

		this.id = id;
		this.repIntmModelId = repIntmModelId;
		this.intmSeq = intmSeq;
		this.intmIdfyNo = intmIdfyNo;
		this.intmSalesStatus = intmSalesStatus;
		this.intmBuyerId = intmBuyerId;
	}
    
}
