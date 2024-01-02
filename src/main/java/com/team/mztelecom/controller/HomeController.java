package com.team.mztelecom.controller;

import java.security.Principal;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Sort;

import com.team.mztelecom.dto.PurRevAttachmentDTO;
import com.team.mztelecom.dto.PurRevBoardDTO;
import com.team.mztelecom.service.CustService;
import com.team.mztelecom.service.ProductService;
import com.team.mztelecom.service.PurRevAttachmentService;
import com.team.mztelecom.service.PurRevBoardService;
import com.team.util.StringUtil;
import com.team.util.Utiles;

@Controller
public class HomeController {

	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	ProductService productService;
	
	@Autowired
	CustService custService;
	
	@Autowired
	PurRevBoardService purRevBoardService;
	
	@Autowired
	PurRevAttachmentService purRevAttachmentService;

	
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
		return "cart";
	}
	
	@GetMapping(value = "/phoneplan")
	public String phoneplan(Locale locale, Model model) {
		return "content/phoneplan";
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
	
	/**
	 * 구매후기 목록 - 김시우
	 * 
	 * @param model
	 * @param pageable
	 * @param keyWord
	 * @param catgo
	 * @return
	 */
	@GetMapping(value = "/purRevBoard")
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
	    
	    List<PurRevBoardDTO> temp = new ArrayList<>();
	    
	    temp.addAll(purRevBoardList.getContent());
	    
	    logger.debug("temp :: " + StringUtil.toString(temp));

	    return "content/purRevBoard";
	}
	
	/**
	 * 구매후기 작성페이지
	 * 
	 * @param locale
	 * @param model
	 * @param principal
	 * @return
	 */
	@GetMapping(value = "/purRevWrite")
	public String purRevWrite(Locale locale, Model model, Principal principal) {
		
		logger.debug("구매후기 글쓰기 진입");
		logger.debug(principal.getName());
		
		// 현재 로그인한 사용자의 이름
        String custNm = custService.findName(principal.getName());
        
        model.addAttribute("custNm", custNm);
		
		return "content/purRevWrite";
	}
	
	
	/**
	 * 구매후기 열람 - 김시우
	 * 
	 * @param model
	 * @param id
	 * @param principal
	 * @return
	 */
	@GetMapping(value = "/purRevView/{id}")
	public String purRevView(Model model, @PathVariable Long id, Principal principal) {
		
		logger.debug("구매후기 열람 진입");
		
		PurRevBoardDTO purRevBoardDTO = new PurRevBoardDTO();
		List<PurRevAttachmentDTO> outPurRevAttachmentDTO = new ArrayList<>();
		
		// 게시물 조회
		purRevBoardDTO = purRevBoardService.purRevView(id);
		
		// 작성된 게시물에 있는 첨부파일 조회
		outPurRevAttachmentDTO = purRevAttachmentService.getAttachment(id);
		
		for(int i =0; i < outPurRevAttachmentDTO.size(); i++) {
			logger.debug("outPurRevAttachmentDTO :: " + outPurRevAttachmentDTO.get(i).getFilePath());
		}
		
		String custId = purRevBoardDTO.getCustBasDTO().get(0).getCustId();
		/*
		 * 현재 로그인한 사용자가 있을 경우 로그인 사용자를 넘겨주고
		 * 그렇지 않은 경우. 즉, null인 경우는 null로 할당
		 */
	    String sessionId = Optional.ofNullable(principal)
	            .map(Principal::getName)
	            .orElse(null);

	    model.addAttribute("sessionId", sessionId);
	    model.addAttribute("custId", custId);
		model.addAttribute("id", id);
		model.addAttribute("purRevBoard", purRevBoardDTO);
		model.addAttribute("purRevAttachment", outPurRevAttachmentDTO);
		
		return "content/purRevView";
	}
	
	
	@GetMapping(value = "/admin")
	public String admin(Locale locale, Model model) {
		
		return "admin";
	}
	
}
