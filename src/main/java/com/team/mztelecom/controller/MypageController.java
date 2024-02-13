package com.team.mztelecom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.team.mztelecom.dto.CustBasDTO;
import com.team.mztelecom.service.MypageService;
import com.team.util.StringUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MypageController {
	
	private static final Logger logger = LoggerFactory.getLogger(MypageController.class);

	private final MypageService mypageService;
	
	/**
	 * 회원정보 수정 컨트롤러 - 김시우
	 * 
	 * @param inCustBasDTO
	 * @return
	 */
	@PostMapping(value = "/update/custInfo")
	public String updateCustInfo(@ModelAttribute CustBasDTO inCustBasDTO) {
		logger.debug("inCustBasDTO :: " + StringUtil.toString(inCustBasDTO));
		
		mypageService.updateCustInfo(inCustBasDTO);
		
		return "/myPage?tab=editMemberInformation";
		
	}
	
	/**
	 * 회원 탈퇴 컨트롤러 - 김시우
	 * 
	 * @param inCustBasDTO
	 * @return
	 */
	@PostMapping(value = "/custWithdrawal")
	public String deleteCust(@ModelAttribute CustBasDTO inCustBasDTO) {
		
		logger.debug("inCustBasDTO :: " + StringUtil.toString(inCustBasDTO));
		
		mypageService.deleteCust(inCustBasDTO);
		
		return "redirect:/logout";
	}

}
