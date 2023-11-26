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
	
	@GetMapping(value = "/")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		logger.debug("메인페이지 진입");
		
		return "content/home";
	}
	
	@GetMapping(value = "/login")
	public String login(Locale locale, Model model) {
		return "content/login";
	}
	
	@GetMapping(value = "/signup")
	public String signup(Locale locale, Model model) {
		return "content/signup";
	}
	
	@GetMapping(value = "/find")
	public String find(Locale locale, Model model) {
		
		logger.debug("아이디 비밀번호 찾기 진입");
		return "content/findIdOrPw";
	}
	
	@GetMapping(value = "/cart")
	public String cart(Locale locale, Model model) {
		return "content/cart";
	}
	
	@GetMapping(value = "/product")
	public String product(Locale locale, Model model) {
		return "content/product";
	}
	
	@GetMapping(value = "/phoneplan")
	public String phoneplan(Locale locale, Model model) {
		return "content/phoneplan";
	}
	
	@GetMapping(value = "/productDetail")
	public String productDetail(Locale locale, Model model) {
		

	    return "content/productDetail";
	}
 
	@GetMapping(value = "/support")
	public String support(Locale locale, Model model) {
  
		return "content/support";
	}
	
	@GetMapping(value = "/myPage")
	public String myPage(Locale locale, Model model) {
		
		logger.debug("마이페이지");
		
		return "myPage";
	}
	
	@GetMapping(value = "/inquiryDetail")
	public String inquiryDetail(Locale locale, Model model) {
		
		logger.debug("마이페이지/문의내역/문의내용");
		
		return "content/inquiryDetail";
	}
	
	@GetMapping(value = "/purRevBoard")
	public String purRevBoard(Locale locale, Model model) {
		
		logger.debug("구매후기게시판 진입");
		
		return "content/purRevBoard";
	}
	
	@GetMapping(value = "/purRevBoard/purRevWrite")
	public String purRevWrite(Locale locale, Model model) {
		
		logger.debug("구매후기 글쓰기 진입");
		
		return "content/purRevWrite";
	}
	
	@GetMapping(value = "/purRevView")
	public String purRevView(Locale locale, Model model) {
		
		logger.debug("구매후기 열람 진입");
		
		return "content/purRevView";
	}
	
	
	@GetMapping(value = "/admin")
	public String admin(Locale locale, Model model) {
		
		return "admin";
	}
	
}
