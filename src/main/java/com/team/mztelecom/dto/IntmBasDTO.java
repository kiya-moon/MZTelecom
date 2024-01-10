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
	Long   id;						// id
	List<String> intmModelColor;	// 기기색상
	String intmNm;					// 단말기 이름
	String intmKorNm;				// 단말기 한글 이름
	List<String> intmGB;			// 기기 용량
	List<String> intmPrice;			// 기기 값
	boolean isLiked;				// 찜하기
	List<IntmImgDTO> intmImgs;		// 상품 이미지
	
	
	@Builder
	public IntmBasDTO(Long id, List<String> intmModelColor, String intmNm,
			String intmKorNm, List<String> intmGB, List<String> intmPrice, boolean isLiked, List<IntmImgDTO> intmImgs) {
		
		this.id = id;
		this.intmModelColor = intmModelColor;
		this.intmNm = intmNm;
		this.intmKorNm = intmKorNm;
		this.intmGB = intmGB;
		this.intmPrice = intmPrice;
		this.isLiked = isLiked;
		this.intmImgs = intmImgs;
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
				.isLiked(isLiked)
				.intmImgs(intmImgList)
				.build();
				
	}


}

