package com.team.mztelecom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.team.mztelecom.service.PurRevBoardService;
import com.team.util.StringUtil;

@Controller
public class PurRevBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(PurRevBoardController.class);
	
	@Autowired
	PurRevBoardService purRevBoardService;
	
	// 글 작성 페이지
	@PostMapping(value = "/purRevWrite")
	public String write(@RequestParam("selectedCategory")String selectedCategory
						,@RequestParam("title")String title
						,@RequestParam("contents")String contents
						,@RequestParam("fileName") MultipartFile fileName) {
		
		logger.debug("category :: " + selectedCategory);
		logger.debug("contents :: " + contents);
		logger.debug("fileName :: " + StringUtil.toString(fileName));
		
		
		logger.debug("보내기 완료");
		
		return "redirect:/purRevBoard/**";
	}
	
	
	
	// 작성된 글 보는 페이지
//	@GetMapping(value="/purRevView/{id}")
	public String viewPurRev() {
		
		return "";
	}
	
	
	
}
