package com.team.mztelecom.controller;

import java.security.Principal;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.dto.SysCdDTO;
import com.team.mztelecom.service.CustService;
import com.team.mztelecom.service.CustWishService;
import com.team.mztelecom.service.ProductService;
import com.team.mztelecom.service.SysCdBasService;
import com.team.util.Utiles;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final ProductService productService;

	private final CustWishService custWishService;
	
	private final SysCdBasService sysCdBasService;
	
	private final CustService custService;
	
	/**
	 * 모바일 기기 페이지 진입 - 박지윤
	 * 
	 * @param locale
	 * @param model
	 * @param sortBy
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping(value = "/product")
	public String product(Locale locale, Model model,
			@RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
		
		logger.debug("상품 시작");

		Page<IntmBasDTO> productPage = productService.getAllProductsWithImages(sortBy, page, size);
		
		// 요금 오픈 여부
		SysCdDTO sysCdDTO = SysCdDTO.builder()
				.sys_cd_group_id("rate_plan_group")
				.sys_cd_id("rate_plan_details_yn")
				.build();
		
		String ratePlanOpnYn = sysCdBasService.getSysCd(sysCdDTO);
		
        model.addAttribute("product", productPage);
        model.addAttribute("totalPages", productPage.getTotalPages());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("sortBy", sortBy);
        model.addAttribute("ratePlanOpnYn", ratePlanOpnYn);
        
		return "content/product";
	}
	
	/**
	 * 모바일 상세 페이지 진입 - 박지윤
	 * 
	 * @param productId
	 * @param model
	 * @param principal
	 * @return
	 */
	@GetMapping(value = "/productDetail/{productId}")
	public String productDetail(@PathVariable Long productId, Model model, Principal principal) {
		
		int wishCnt;				// 찜하기횟수
		boolean isLiked = false;	// 찜하기 유무
		
		IntmBasDTO outDTO = productService.getProductById(productId);
		
		/*
		 * 찜하기 기능 추가 - 김시우 
		 * 
		 * 로그인 되어 있을 시
		 */
		if(!Utiles.isNullOrEmpty(principal)) 
		{
			// 찜하기 체크
			wishCnt = custWishService.getWish(productId, principal.getName());
			
			/*
			 * 로그인 한 고객이 해당 상품을 찜하기 했는지 
			 * wishCnt가 존재하면서 1 일때
			 */
			if(!Utiles.isNullOrEmpty(wishCnt) && wishCnt == 1) {
				
				isLiked = true;
				
				model.addAttribute("isLiked", isLiked);
			}
		}
		
		
		model.addAttribute("product", outDTO);

		boolean isLoggedIn = custService.isLoggedIn();
		
		if (!isLoggedIn) {
	        model.addAttribute("loginRequired", true);
	    }

	    return "content/productDetail";
	}
	
}
