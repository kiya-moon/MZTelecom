package com.team.mztelecom.controller;

import java.security.Principal;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.dto.SysCdDTO;
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
	
	@GetMapping(value = "/product")
	public String product(Locale locale, Model model) {
		logger.debug("상품 시작");
		
		List<IntmBasDTO> productList = productService.getAllProductsWithImages();
		
		// 요금 오픈 여부
		SysCdDTO sysCdDTO = SysCdDTO.builder()
				.sys_cd_group_id("rate_plan_group")
				.sys_cd_id("rate_plan_details_yn")
				.build();
		
		String ratePlanOpnYn = sysCdBasService.getSysCd(sysCdDTO);
		
        model.addAttribute("productList", productList);
        model.addAttribute("ratePlanOpnYn", ratePlanOpnYn);
        
		return "content/product";
	}
	
	
	@GetMapping(value = "/productDetail/{productId}")
	public String productDetail(@PathVariable Long productId, Model model, Principal principal) {
		
		int wishCnt;
		boolean isLiked = false;	
		
		IntmBas product = productService.getProductById(productId);
		
		if(!Utiles.isNullOrEmpty(principal)) 
		{
			
			wishCnt = custWishService.getWish(productId, principal.getName());
			
			if(!Utiles.isNullOrEmpty(wishCnt) && wishCnt == 1) {
				
				isLiked = true;
				
				model.addAttribute("isLiked", isLiked);
			}
		}
		
		
		model.addAttribute("product", product);

	    return "content/productDetail";
	}
	
}
