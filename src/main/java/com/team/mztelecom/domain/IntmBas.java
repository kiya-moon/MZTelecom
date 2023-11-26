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
public class IntmBas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String repIntmModelId;	// 대표기기모델아이디

    private String intmModelColor;	// 기기색상
    
    private String intmSeq; 		// 기기일련번호
    
    @NotNull
    @Column(unique = true)
    private String intmIdfyNo;		// 기기식별번호

    @NotNull
    private String intmNm;			// 단말기 이름
    
    @NotNull
    private String intmKorNm;		// 단말기 한글 이름
    
    @NotNull
    private String intmGB;			// 기기 용량
    
    @NotNull
    private String intmPrice;		// 기기 값

    private String intmSalesStatus; // 기기판매여부

    private String intmBuyerId;		// 기기구매자아이디
    
    @Builder
	public IntmBas(Long id, String repIntmModelId, String intmModelColor, String intmSeq,
			String intmIdfyNo, String intmNm, String intmKorNm, String intmGB,
			String intmPrice, String intmSalesStatus, String intmBuyerId) {
    	this.id = id;
		this.repIntmModelId = repIntmModelId;
		this.intmModelColor = intmModelColor;
		this.intmSeq = intmSeq;
		this.intmIdfyNo = intmIdfyNo;
		this.intmNm = intmNm;
		this.intmKorNm = intmKorNm;
		this.intmGB = intmGB;
		this.intmPrice = intmPrice;
		this.intmSalesStatus = intmSalesStatus;
		this.intmBuyerId = intmBuyerId;
	}
    
    
    
    
}
