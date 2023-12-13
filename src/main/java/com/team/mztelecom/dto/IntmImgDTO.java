package com.team.mztelecom.dto;

import com.team.mztelecom.domain.IntmImg;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IntmImgDTO {
	Long	id;
	String	intmNm;				// 단말기 이름
	String	repIntmModelId;		// 대표기기모델아이디
	String	imgName;			// 이미지 이름
	String	imgPath;			// 이미지 경로
	
	@Builder
	public IntmImgDTO(Long id, String repIntmModelId, String intmNm, String imgName, String imgPath) {
		this.id = id;
		this.repIntmModelId = repIntmModelId;
		this.intmNm = intmNm;
		this.imgName = imgName;
		this.imgPath = imgPath;
	}
	
	
	public IntmImg toEntity() {
		return IntmImg.builder()
				.id(id)
				.intmNm(intmNm)
				.repIntmModelId(repIntmModelId)
				.imgName(imgName)
				.imgPath(imgPath)
				.build();
				
	}
}

