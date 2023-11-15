package com.team.mztelecom.dto;

/**
 * 
 * 단말기기 기본 DTO
 * 
 * @author USER
 *
 */

public class IntmBasDTO {
    
	String repIntmModelId;			// 대표기기모델아이디
	String intmSeq;					// 기기일련번호
	String intmModelColor;			// 기기색상
	String intmIdfyNo;				// 기기식별번호
	String intmNm;					// 단말기 이름
	String intmSalesStatus;			// 기기판매여부
	String intmBuyerId;				// 기기구매자아이디(cust_id)
	
	
	public String getRepIntmModelId() {
		return repIntmModelId;
	}
	public void setRepIntmModelId(String repIntmModelId) {
		this.repIntmModelId = repIntmModelId;
	}
	public String getIntmSeq() {
		return intmSeq;
	}
	public void setIntmSeq(String intmSeq) {
		this.intmSeq = intmSeq;
	}
	public String getIntmModelColor() {
		return intmModelColor;
	}
	public void setIntmModelColor(String intmModelColor) {
		this.intmModelColor = intmModelColor;
	}
	public String getIntmIdfyNo() {
		return intmIdfyNo;
	}
	public void setIntmIdfyNo(String intmIdfyNo) {
		this.intmIdfyNo = intmIdfyNo;
	}
	public String getIntmNm() {
		return intmNm;
	}
	public void setIntmNm(String intmNm) {
		this.intmNm = intmNm;
	}
	public String getIntmSalesStatus() {
		return intmSalesStatus;
	}
	public void setIntmSalesStatus(String intmSalesStatus) {
		this.intmSalesStatus = intmSalesStatus;
	}
	public String getIntmBuyerId() {
		return intmBuyerId;
	}
	public void setIntmBuyerId(String intmBuyerId) {
		this.intmBuyerId = intmBuyerId;
	}
	
	

}
