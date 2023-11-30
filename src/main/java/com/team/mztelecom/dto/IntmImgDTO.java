package com.team.mztelecom.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class IntmImgDTO {
	String repIntmModelId;		// 대표기기모델아이디
	String intmNm;				// 단말기 이름
	String imgName;				// 이미지 이름
	String imgPath;				// 이미지 경로
}
