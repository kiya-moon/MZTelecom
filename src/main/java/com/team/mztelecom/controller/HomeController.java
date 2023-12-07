package com.team.mztelecom.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort;

import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.domain.PurRevBoard;
import com.team.mztelecom.dto.PurRevBoardDTO;
import com.team.mztelecom.service.CustService;
import com.team.mztelecom.service.ProductService;
import com.team.mztelecom.service.PurRevBoardService;
import com.team.util.StringUtil;
import com.team.util.Utiles;

@Controller
public class HomeController {

	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CustService custServicev;
	
	@Autowired
	PurRevBoardService purRevBoardService;

	
	@GetMapping(value = "/")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		logger.debug("메인페이지 진입");
		
		return "content/home";
	}
	
	@GetMapping(value = "/login")
	@PreAuthorize("isAnonymous()")
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
	@PreAuthorize("isAuthenticated()")
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
	@PreAuthorize("isAuthenticated()")
	public String myPage(Locale locale, Model model) {
		
		logger.debug("마이페이지");
		
		return "myPage";
	}
	
	@GetMapping(value = "/inquiryDetail")
	public String inquiryDetail(Locale locale, Model model) {
		
		logger.debug("마이페이지/문의내역/문의내용");
		
		return "content/inquiryDetail";
	}
	
	@GetMapping(value = "/purRevBoard/**")
	public String purRevBoard(Model model,
	                          @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
	                          @RequestParam(name = "keyWord", required = false) String keyWord,
	                          @RequestParam(name = "catgo", required = false) String catgo) {
	    logger.debug("구매후기게시판 진입");
	    
	    Page<PurRevBoardDTO> purRevBoardList;
	    if (Utiles.isNullOrEmpty(keyWord)) 
	    {
	        logger.debug("기본 조회");
	        purRevBoardList = purRevBoardService.PurRevBoardList(pageable);
	    } 
	    else 
	    {
	        logger.debug("검색 조회");
	        purRevBoardList = purRevBoardService.searchingPurRevBoard(keyWord, pageable, catgo);
	        // 검색 조건도 뷰로 전달
	        model.addAttribute("keyWord", keyWord);
	        model.addAttribute("catgo", catgo);
	        model.addAttribute("selectedKeyWord", Utiles.isNullOrEmpty(keyWord) ? "" : keyWord);
	        model.addAttribute("selectedCatgo", Utiles.isNullOrEmpty(catgo) ? "" : catgo);
	    }
	    
	    logger.debug("getTotalPages ::: " + purRevBoardList.getTotalPages());
	    logger.debug("getSort ::: " + purRevBoardList.getSort());
	    logger.debug("getPageable ::: " + purRevBoardList.getPageable());
	    logger.debug("getNumber ::: " + purRevBoardList.getNumber());
	    logger.debug("getSize ::: " + purRevBoardList.getSize());

	    model.addAttribute("purRevBoardList", purRevBoardList);

	    logger.debug("controller 리턴 전");

	    return "content/purRevBoard";
	}
	
	@GetMapping(value = "/purRevWrite")
	@PreAuthorize("isAuthenticated()")
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
	@PreAuthorize("hesAuthority('ADMIN')")
	public String admin(Locale locale, Model model) {
		
		return "admin";
	}
	
}
