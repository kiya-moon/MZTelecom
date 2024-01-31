package com.team.mztelecom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team.mztelecom.service.SupportService;

@Controller
public class SupportController {
	
	private static final Logger logger = LoggerFactory.getLogger(SupportController.class);
	
	@Autowired
	SupportService supportService;
	
	/**
	 * 문의하기 - 김시우
	 * 
	 * @param category
	 * @param emailId
	 * @param domain
	 * @param contents
	 * @return
	 */
	@PostMapping(value = "/sendQnA")
	public String sendQnA(@RequestParam("selectedCategory") String category
						,@RequestParam("email") String emailId
						,@RequestParam("qnaDomainList") String domain
						,@RequestParam("contents") String contents) {
		
		logger.debug("category :: " + category);
		logger.debug("email ::: " + emailId);
		logger.debug("qna-domain-list :: " + domain);
		logger.debug("contents :: " + contents);
		
		String email = "";
		StringBuffer buff = new StringBuffer();
		
		buff.append(emailId).append("@").append(domain);
		email = buff.toString();
		
		supportService.sendQnA(category, email, contents);
		
		
		return "redirect:/";
	}
	
	
	
}
