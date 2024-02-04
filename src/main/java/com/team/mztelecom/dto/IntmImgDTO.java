package com.team.mztelecom.dto;

import com.team.mztelecom.domain.IntmImg;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IntmImgDTO {
	Long	id;
	String	intmNm;				// 단말기 이름
	String	imgName;			// 이미지 이름
	String 	imgDetailNm;		// 이미지 상세
	String	imgPath;			// 이미지 경로
	String	imgDetailPath;		// 이미지 상세 경로
	
	@Builder
	public IntmImgDTO(Long id, String intmNm, String imgName, String imgDetailNm, String imgPath, String imgDetailPath) {
		this.id = id;
		this.intmNm = intmNm;
		this.imgName = imgName;
		this.imgDetailNm = imgDetailNm;
		this.imgPath = imgPath;
		this.imgDetailPath = imgDetailPath;
	}
	
	
	public IntmImg toEntity() {
		return IntmImg.builder()
				.id(id)
				.intmNm(intmNm)
				.imgName(imgName)
				.imgDetailNm(imgDetailNm)
				.imgPath(imgPath)
				.imgDetailPath(imgDetailPath)
				.build();
				
	}
}

