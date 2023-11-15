package com.team.mztelecom.dto;

/**
 * 
 * 단말기기 이미지 DTO
 * 
 * @author USER
 *
 */

public class IntmImgDTO {
	
	String intmNm;				// 단말기 이름
	String repIntmModelId;		// 대표기기모델아이디
	String imgId;				// 이미지 아이디
	String imgNm;				// 이미지 이름
	String imgPath;				// 이미지 경로
	
	
	public String getIntmNm() {
		return intmNm;
	}
	public void setIntmNm(String intmNm) {
		this.intmNm = intmNm;
	}
	public String getRepIntmModelId() {
		return repIntmModelId;
	}
	public void setRepIntmModelId(String repIntmModelId) {
		this.repIntmModelId = repIntmModelId;
	}
	public String getImgId() {
		return imgId;
	}
	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	public String getImgNm() {
		return imgNm;
	}
	public void setImgNm(String imgNm) {
		this.imgNm = imgNm;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

}
