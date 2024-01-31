package com.team.mztelecom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team.mztelecom.dto.QnADTO;
import com.team.mztelecom.service.AdminService;

@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	AdminService adminService;
	
	@PostMapping(value="/answrCmplt")
	public String answrQnA(@RequestParam("qna-id") Long id) {
		
		logger.debug("id :: " + id);
		
		QnADTO inDTO = QnADTO.builder()
						.id(id)
						.build();
		
		adminService.deleteQna(inDTO);
		
		return "redirect:/admin?tab=qna";
	}
}
