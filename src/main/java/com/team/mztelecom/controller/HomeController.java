package com.team.mztelecom.controller;

import java.util.*;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.service.CustService;
import com.team.mztelecom.service.ProductService;

@Controller
public class HomeController {

	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CustService custServicev;

	
	@GetMapping(value = "/")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		logger.debug("메인페이지 진입");
		
		String id = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		GrantedAuthority auth = iter.next();
		String role = auth.getAuthority();
		
		
		model.addAttribute("id", id);
		model.addAttribute("role", role);
		
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
		logger.debug("상품 시작");
		
		List<IntmBas> productList = productService.getAllProductsWithImages();
		
        model.addAttribute("productList", productList);
        
		return "content/product";
	}
	
	
	@GetMapping(value = "/phoneplan")
	public String phoneplan(Locale locale, Model model) {
		return "content/phoneplan";
	}
	
	@GetMapping(value = "/productDetail/{productId}")
	public String productDetail(@PathVariable Long productId, Model model) {
		
		IntmBas product = productService.getProductById(productId);
		
		model.addAttribute("product", product);

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
