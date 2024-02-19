package com.team.mztelecom.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.team.mztelecom.dto.CustBasDTO;
import com.team.mztelecom.dto.FAQDTO;
import com.team.mztelecom.dto.InquiryCustDTO;
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.dto.OrdersDTO;
import com.team.mztelecom.dto.PurRevAttachmentDTO;
import com.team.mztelecom.dto.PurRevBoardDTO;
import com.team.mztelecom.dto.QnADTO;
import com.team.mztelecom.dto.SysCdDTO;
import com.team.mztelecom.service.AdminService;
import com.team.mztelecom.service.AttachmentService;
import com.team.mztelecom.service.CustService;
import com.team.mztelecom.service.MypageService;
import com.team.mztelecom.service.OrderService;
import com.team.mztelecom.service.ProductService;
import com.team.mztelecom.service.PurRevBoardService;
import com.team.mztelecom.service.SupportService;
import com.team.mztelecom.service.SysCdBasService;
import com.team.util.StringUtil;
import com.team.util.Utiles;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	private final ProductService productService;

	private final CustService custService;

	private final PurRevBoardService purRevBoardService;

	private final AttachmentService attachmentService;

	private final AdminService adminService;
	
	private final MypageService mypageService;
	
	private final SupportService supportService;
	
	private final OrderService orderService;
	
	private final SysCdBasService sysCdBasService;
	
	/**
	 * 메인 페이지
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/")
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		logger.debug("메인페이지 진입");

		return "content/home";
	}

	/**
	 * 로그인 페이지
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/login")
	public String login(Locale locale, Model model) {
		return "content/login";
	}

	/**
	 * 회원가입 페이지
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/signup")
	public String signup(Locale locale, Model model) {
		return "content/signup";
	}

	/**
	 * 아이디 비밀번호 찾기 페이지
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/find")
	public String find(Locale locale, Model model) {
		logger.debug("아이디 비밀번호 찾기 진입");
		return "content/findIdOrPw";
	}

	/**
	 * 모바일 요금제 페이지
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/phoneplan")
	public String phoneplan(Locale locale, Model model) {
		return "content/phoneplan";
	}
	
	/**
	 * 고객지원 페이지
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/support")
	public String support(Locale locale, Model model) {

		List<FAQDTO> FAQList = supportService.getFAQ();
		
		model.addAttribute("FAQList", FAQList);
		
		return "content/support";
	}

	/**
	 * 마이페이지 - 김시우, 박지윤
	 * 
	 * @param locale
	 * @param model
	 * @param principal
	 * @return
	 */
	@GetMapping(value = "/myPage")
	public String myPage(Locale locale, Model model, Principal principal) {
		
		logger.debug("mypage 진입");
		
		String custId;
		
		// 로그인을 했을 시
		if(!Utiles.isNullOrEmpty(principal))
		{
			custId = principal.getName();	// 현재 로그인 한 고객 아이디
			CustBasDTO inCustBasDTO = CustBasDTO.builder()
										.custId(custId)
										.build();
			
			CustBasDTO outCustBasDTO = custService.getMember(inCustBasDTO);
			List<IntmBasDTO> wishList = mypageService.listMyWish(inCustBasDTO.getCustId());
			
			List<OrdersDTO> outOrdersDTO = orderService.getOrdersByCustBas(inCustBasDTO);
			
			// 요금제 오픈 여부
			SysCdDTO sysCdDTO = SysCdDTO.builder()
					.sys_cd_group_id("rate_plan_group")
					.sys_cd_id("rate_plan_details_yn")
					.build();
			
			String ratePlanOpnYn = sysCdBasService.getSysCd(sysCdDTO);
			
			
			model.addAttribute("ratePlanOpnYn", ratePlanOpnYn);
			model.addAttribute("custInfo", outCustBasDTO);
			model.addAttribute("wishList", wishList);
			model.addAttribute("ordersList", outOrdersDTO);
		}
		

		logger.debug("마이페이지");

		return "myPage";
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
		
		// 검색 키워드가 없을 때
		if (Utiles.isNullOrEmpty(keyWord)) 
		{
			logger.debug("기본 조회");
			purRevBoardList = purRevBoardService.PurRevBoardList(pageable);
			
		} 
		// 검색 키워드가 있을 때
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
	 * 구매후기 작성페이지 - 김시우
	 * 
	 * @param locale
	 * @param model
	 * @param principal
	 * @return
	 */
	@GetMapping(value = "/purRevWrite")
	public String purRevWrite(Locale locale, Model model, Principal principal) {

		logger.debug("구매후기 글쓰기 진입");
		String custId = Optional.ofNullable(principal).map(Principal::getName).orElse(null);
		
		CustBasDTO inDTO = CustBasDTO.builder()
							.custId(custId)
							.build();
		
		String custNm = custService.findName(inDTO);
		// 현재 로그인한 사용자의 이름
		
		// 구매후기 작성시 카테고리 선택을 위한 상품 조회
		List<IntmBasDTO> outIntmDTO = productService.getAllIntmList();

		model.addAttribute("custNm", custNm);
		model.addAttribute("intm", outIntmDTO);

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

		logger.debug("id :: " + id);
		// 게시물 조회
		purRevBoardDTO = purRevBoardService.purRevView(id);

		// 작성된 게시물에 있는 첨부파일 조회
		outPurRevAttachmentDTO = attachmentService.getAttachment(id);

		String custId = purRevBoardDTO.getCustBasDTO().get(0).getCustId();
		
		/*
		 * 현재 로그인한 사용자가 있을 경우 로그인 사용자를 넘겨주고 그렇지 않은 경우. 즉, null인 경우는 null로 할당
		 */
		String sessionId = Optional.ofNullable(principal).map(Principal::getName).orElse(null);
		logger.debug("sessionId :: " + sessionId);
		logger.debug("custId :"+ custId);

		model.addAttribute("sessionId", sessionId);
		model.addAttribute("custId", custId);
		model.addAttribute("id", id);
		model.addAttribute("purRevBoard", purRevBoardDTO);
		model.addAttribute("purRevAttachment", outPurRevAttachmentDTO);

		return "content/purRevView";
	}

	
	/**
	 * 관리자 페이지
	 * 
	 * @param keyword
	 * @param sortBy
	 * @param page
	 * @param size
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/admin")
	public String admin(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(defaultValue = "createdAt") String sortBy,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size, Model model) {

		logger.debug("관리자페이지 진입");

		// 회원 관리 - 문기연
		Pageable pageable = PageRequest.of(page, size, Sort.by("custId"));
		logger.debug("keyword :: " + keyword);
		
		Page<InquiryCustDTO> custInfoPage = adminService.getCustInfoPage(keyword, pageable);
		logger.debug("custInfoPage :: " + custInfoPage);
		
		List<InquiryCustDTO> custInfoList = adminService.getCustInfoList();
		
		// 상품현황 - 문기연
		List<OrdersDTO> ordersList = adminService.getOrdersList();
		
		Pageable ordersPage = PageRequest.of(page, size, Sort.by("paymentDate"));
		
		Page<OrdersDTO> ordersPage2 = adminService.getOrdersPage(ordersPage);
		
		// 문의 내역 조회 - 김시우
		List<QnADTO> outQnAList =  supportService.getQnA();
		
		Page<IntmBasDTO> productPage = productService.getAllProductsWithImages(sortBy, page, size);
		
		model.addAttribute("custInfoPage", custInfoPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("custInfoList", custInfoList);
		model.addAttribute("ordersList", ordersList);
		model.addAttribute("ordersPage", ordersPage2);
		model.addAttribute("outQnAList", outQnAList);	// 문의 내역
		model.addAttribute("product", productPage);
        model.addAttribute("totalPages", productPage.getTotalPages());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("sortBy", sortBy);

		return "admin";
	}
	
}
