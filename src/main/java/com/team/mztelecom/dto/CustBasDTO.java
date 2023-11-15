package com.team.mztelecom.dto;


/**
 * 
 * 고객 정보 DTO
 * 
 * @author USER
 *
 */
public class CustBasDTO {
	
	String custId;				// 고객아이디
	String custNm;				// 고객명
	String custPassword;		// 고객패스워드
	String custIdfyNo;			// 고객식별번호
	String custBirth;			// 생년월일(yyyy.mm.dd)
	String custNo;				// 고객번호(전화번호)
	String custSex;			// 고객성별
	String custAddress;		// 고객주소
	String custEmail;			// 고객이메일
	String intmPurStusYn;		// 기기구매여부
	
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getCustPassword() {
		return custPassword;
	}
	public void setCustPassword(String custPassword) {
		this.custPassword = custPassword;
	}
	public String getCustIdfyNo() {
		return custIdfyNo;
	}
	public void setCustIdfyNo(String custIdfyNo) {
		this.custIdfyNo = custIdfyNo;
	}
	public String getCustBirth() {
		return custBirth;
	}
	public void setCustBirth(String custBirth) {
		this.custBirth = custBirth;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getCustSex() {
		return custSex;
	}
	public void setCustSex(String custSex) {
		this.custSex = custSex;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	public String getIntmPurStusYn() {
		return intmPurStusYn;
	}
	public void setIntmPurStusYn(String intmPurStusYn) {
		this.intmPurStusYn = intmPurStusYn;
	}
	
}
