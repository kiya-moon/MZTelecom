package com.team.mztelecom.dto;

/**
 * 
 * 서비스 계약 기본 DTO
 * 
 * @author USER
 *
 */

public class SvcContBasDTO {
	
    String custId;				// 고객아이디
    String svcContId;			// 서비스계약아이디
    String svcNo;				// 서비스번호(전화번호)
    String memberStatus;      	// 회원유무
    String rgs_dt;				// 가입일자
    String wth_dt;				// 탈퇴일자
    
    
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getSvcContId() {
		return svcContId;
	}
	public void setSvcContId(String svcContId) {
		this.svcContId = svcContId;
	}
	public String getSvcNo() {
		return svcNo;
	}
	public void setSvcNo(String svcNo) {
		this.svcNo = svcNo;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	public String getRgs_dt() {
		return rgs_dt;
	}
	public void setRgs_dt(String rgs_dt) {
		this.rgs_dt = rgs_dt;
	}
	public String getWth_dt() {
		return wth_dt;
	}
	public void setWth_dt(String wth_dt) {
		this.wth_dt = wth_dt;
	}
}
