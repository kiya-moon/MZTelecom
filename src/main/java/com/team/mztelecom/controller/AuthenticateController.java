package com.team.mztelecom.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AuthenticateController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@PostMapping(value = "/authenticate")
	public String order(Locale locale, Model model) {
		
		logger.debug("인증 시작");
		
	    return "content/authenticate";
	}
	
}
