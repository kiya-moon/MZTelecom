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
import com.team.mztelecom.service.CustWishService;
import com.team.mztelecom.service.ProductService;
import com.team.util.Utiles;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final ProductService productService;
	
	private final CustWishService custWishService;
	
	@GetMapping(value = "/product")
	public String product(Locale locale, Model model) {
		logger.debug("상품 시작");
		
		List<IntmBasDTO> productList = productService.getAllProductsWithImages();
		
        model.addAttribute("productList", productList);
        
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
