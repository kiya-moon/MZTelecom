package com.team.mztelecom.dto;

import java.util.*;
import java.util.stream.Collectors;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.domain.IntmImg;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IntmBasDTO {
	Long   id;					// id
	String repIntmModelId;		// 대표기기모델아이디
	String intmModelColor;		// 기기색상
	String intmSeq;				// 기기일련번호
	String intmIdfyNo;			// 기기식별번호
	String intmNm;				// 단말기 이름
	String intmKorNm;			// 단말기 한글 이름
	String intmGB;				// 기기 용량
	String intmPrice;			// 기기 값
	String intmSalesStatus;		// 기기판매여부
	String intmBuyerId;			// 기기구매자아이디
	boolean isLiked;			// 찜하기
	List<IntmImgDTO> intmImgs;	// 상품 이미지
	
	
	@Builder
	public IntmBasDTO(Long id, String repIntmModelId, String intmModelColor, String intmSeq, String intmIdfyNo, String intmNm,
			String intmKorNm, String intmGB, String intmPrice, String intmSalesStatus, String intmBuyerId, boolean isLiked, List<IntmImgDTO> intmImgs) {
		
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
		this.isLiked = isLiked;
		this.intmImgs = intmImgs;
	}
	
	public IntmBas toEntity() {
		List<IntmImg> intmImgList = intmImgs.stream()
	            .map(IntmImgDTO::toEntity)
	            .collect(Collectors.toList());
		
		return IntmBas.builder()
				.id(id)
				.repIntmModelId(repIntmModelId)
				.intmModelColor(intmModelColor)
				.intmSeq(intmSeq)
				.intmIdfyNo(intmIdfyNo)
				.intmNm(intmNm)
				.intmKorNm(intmKorNm)
				.intmGB(intmGB)
				.intmPrice(intmPrice)
				.intmSalesStatus(intmSalesStatus)
				.intmBuyerId(intmBuyerId)
				.isLiked(isLiked)
				.intmImgs(intmImgList)
				.build();
				
	}


}

