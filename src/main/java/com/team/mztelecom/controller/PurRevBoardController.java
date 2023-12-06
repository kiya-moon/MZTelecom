package com.team.mztelecom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.team.mztelecom.service.PurRevBoardService;

@Controller
public class PurRevBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(PurRevBoardController.class);
	
	@Autowired
	PurRevBoardService purRevBoardService;
	
	// 글작성 페이지
//	@PostMapping(value = "purRevBoard/purRevWrite.do")
//    @ResponseBody
//	public Map<String, String> purRevWrite(
//			//@RequestParam("writer") String writer
//							@RequestParam("intm-category") String intmCategory
//							,@RequestParam("review-title") String reviewTitle
//							,@RequestParam("review-contents") String reviewContents
//							,@RequestParam("attachment") String attachment)
//	{
//		logger.debug("글작성 확인버튼");
//		
//		Map<String, String> revMap = new HashMap<>();
//		
////		logger.debug("전송 받은 writer :: " + writer);
//		logger.debug("전송 받은 intmCategory :: " + intmCategory);
//		logger.debug("전송 받은 reviewTitle :: " + reviewTitle);
//		logger.debug("전송 받은 reviewContents :: " + reviewContents);
//		logger.debug("전송 받은 attachment :: " + attachment);
//		
////		revMap.put("writer", writer);
//		revMap.put("intmCategory", intmCategory);
//		revMap.put("reviewTitle", reviewTitle);
//		revMap.put("reviewContents", reviewContents);
//		revMap.put("attachment", attachment);
//		
//		 
//		
//		
//		return revMap;
//	}
	@PostMapping(value = "/purRevWrite")
	public String write(String category, String title, String contents, String fileName) {
		
		logger.debug("category :: " + category);
		logger.debug("contents :: " + contents);
		logger.debug("fileName :: " + fileName);
		
		
		logger.debug("보내기 완료");
		
		return "redirect:/";
	}
	
	
	
	// 작성된 글 보는 페이지
//	@GetMapping(value="/purRevView/{id}")
	public String viewPurRev() {
		
		return "";
	}
	
	
	
}
