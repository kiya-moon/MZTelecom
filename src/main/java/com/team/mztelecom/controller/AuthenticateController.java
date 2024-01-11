package com.team.mztelecom.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	
	@PostMapping(value = "/authenticate")
	public String order(Locale locale, Model model
						,@RequestParam("intmKorNm") String intmKorNm
						,@RequestParam("color") String cliColor
						,@RequestParam("option") String cliCpcty) {
		
		logger.debug("인증 시작");
		logger.debug("intmKorNm :: " +  intmKorNm);
		logger.debug("cliColor ::" + cliColor);
		logger.debug("option ::: " + cliCpcty);
		
		temporarySaveDTO.getOrderTmp().add(intmKorNm);
		temporarySaveDTO.getOrderTmp().add(cliColor);
		temporarySaveDTO.getOrderTmp().add(cliCpcty);
		
		
		
	    return "content/authenticate";
	}
	
}
