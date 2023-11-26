package com.team.mztelecom.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductDTO {
	String repIntmModelId;		// 대표기기모델아이디
	String intmModelColor;		// 기기색상
	String intmSeq;				// 기기일련번호
	String intmIdfyNo;			// 기기식별번호
	String intmNm;				// 단말기 이름
	String intmSalesStatus;		// 기기판매여부
	String intmBuyerId;			// 기기구매자아이디
}
