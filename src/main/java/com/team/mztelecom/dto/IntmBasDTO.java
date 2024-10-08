package com.team.mztelecom.dto;

import java.time.LocalDate;
import java.util.*;

import com.team.mztelecom.domain.IntmBas;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IntmBasDTO {
	Long	id;						// id
	Long	cartItemId;				// 장바구니 상품 아이디
	List<String> intmModelColor;	// 기기색상
	String intmNm;					// 단말기 이름
	String intmKorNm;				// 단말기 한글 이름
	List<String> intmGB;			// 기기 용량
	List<String> intmPrice;			// 기기 값
	int wishCnt;					// 찜하기
	List<IntmImgDTO> intmImgs;		// 상품 이미지
	String fee;
	LocalDate createdAt;			// 최신순
	
	
	@Builder
	public IntmBasDTO(Long id, Long cartItemId, List<String> intmModelColor, String intmNm,
			String intmKorNm, List<String> intmGB, List<String> intmPrice, int wishCnt, List<IntmImgDTO> intmImgs, String fee, LocalDate createdAt) {
		
		this.id = id;
		this.cartItemId = cartItemId;
		this.intmModelColor = intmModelColor;
		this.intmNm = intmNm;
		this.intmKorNm = intmKorNm;
		this.intmGB = intmGB;
		this.intmPrice = intmPrice;
		this.wishCnt = wishCnt;
		this.intmImgs = intmImgs;
		this.fee = fee;
		this.createdAt = createdAt;
	}
	
	public IntmBas toEntity() {
		
		IntmBas build = IntmBas.builder()
				.id(id)
				.intmModelColor(intmModelColor)
				.intmNm(intmNm)
				.intmKorNm(intmKorNm)
				.intmGB(intmGB)
				.intmPrice(intmPrice)
				.wishCnt(wishCnt)
				.fee(fee)
				.createdAt(createdAt)
				.build();
				
			return build;
	}


}

