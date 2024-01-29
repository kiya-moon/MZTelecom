package com.team.mztelecom.dto;

import java.util.*;
import java.util.stream.Collectors;

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
	Long   id;						// id
	List<String> intmModelColor;	// 기기색상
	String intmNm;					// 단말기 이름
	String intmKorNm;				// 단말기 한글 이름
	List<String> intmGB;			// 기기 용량
	List<String> intmPrice;			// 기기 값
	int wishCnt;					// 찜하기
	List<IntmImgDTO> intmImgs;		// 상품 이미지
	String fee;
	
	
	@Builder
	public IntmBasDTO(Long id, List<String> intmModelColor, String intmNm,
			String intmKorNm, List<String> intmGB, List<String> intmPrice, int wishCnt, List<IntmImgDTO> intmImgs, String fee) {
		
		this.id = id;
		this.intmModelColor = intmModelColor;
		this.intmNm = intmNm;
		this.intmKorNm = intmKorNm;
		this.intmGB = intmGB;
		this.intmPrice = intmPrice;
		this.wishCnt = wishCnt;
		this.intmImgs = intmImgs;
		this.fee = fee;
	}
	
	public IntmBas toEntity() {
		List<IntmImg> intmImgList = intmImgs.stream()
	            .map(IntmImgDTO::toEntity)
	            .collect(Collectors.toList());
		
		return IntmBas.builder()
				.id(id)
				.intmModelColor(intmModelColor)
				.intmNm(intmNm)
				.intmKorNm(intmKorNm)
				.intmGB(intmGB)
				.intmPrice(intmPrice)
				.wishCnt(wishCnt)
				.intmImgs(intmImgList)
				.fee(fee)
				.build();
				
	}


}

