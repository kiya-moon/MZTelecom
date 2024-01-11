package com.team.mztelecom.controller;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.service.CustService;
import com.team.mztelecom.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final ProductService productService;
	
	private final CustService custService;
	
	@GetMapping(value = "/product")
	public String product(Locale locale, Model model) {
		logger.debug("상품 시작");
		
		List<IntmBasDTO> productList = productService.getAllProductsWithImages();
		
        model.addAttribute("productList", productList);
        
		return "content/product";
	}
	
	
	@GetMapping(value = "/productDetail/{productId}")
	public String productDetail(@PathVariable Long productId, Model model) {
		
		IntmBas product = productService.getProductById(productId);
		
		model.addAttribute("product", product);

	    return "content/productDetail";
	}
	
	@PutMapping(value = "/product/{productId}/liked")
	public ResponseEntity<Map<String, Boolean>> toggleProductLiked(@PathVariable Long productId, @ModelAttribute IntmBasDTO intmBasDTO) {
	    boolean isLiked = productService.toggleProductLiked(productId, intmBasDTO);
	    
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("isLiked", isLiked);
	    response.put("isLoggedIn", custService.isLoggedIn());

	    return ResponseEntity.ok(response);
	}
	
	
}
