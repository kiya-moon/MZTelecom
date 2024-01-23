package com.team.mztelecom.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team.mztelecom.dto.TemporarySaveDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AuthenticateController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticateController.class);
	
	@Autowired
	TemporarySaveDTO temporarySaveDTO;
	
	@GetMapping(value = "/authenticate")
	public String authenticate(Locale locale, Model model) {
		
		return "content/authenticate";
	}
	
	
	@PostMapping(value = "/authenticate")
	public String authenticate(Locale locale, Model model
						,@RequestParam("intmKorNm") String intmKorNm
						,@RequestParam("color") String cliColor
						,@RequestParam("option") String cliCpcty
						,@RequestParam("priceList") String cliPrice) {
		
		logger.debug("인증 시작");
		logger.debug("intmKorNm :: " +  intmKorNm);
		logger.debug("cliColor ::" + cliColor);
		logger.debug("option ::: " + cliCpcty);
		logger.debug("price ::: " + cliPrice);
		
		temporarySaveDTO.getOrderTmp().add(intmKorNm);
		temporarySaveDTO.getOrderTmp().add(cliColor);
		temporarySaveDTO.getOrderTmp().add(cliCpcty);
		temporarySaveDTO.getOrderTmp().add(cliPrice);
		
	    return "content/authenticate";
	}
	
}
