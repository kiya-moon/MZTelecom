package com.team.mztelecom.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	
	@GetMapping("/")
    public String getBoardIndexPage(Locale locale, Model model){
		
		logger.info("테스트해본다");
		
        return "templates/contentsTest";
    }
}
