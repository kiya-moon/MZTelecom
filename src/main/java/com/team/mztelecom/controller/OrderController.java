package com.team.mztelecom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class OrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final ProductService productService;
	
	
	@GetMapping(value = "/order/{productId}")
	public String order(@PathVariable Long productId, Model model) {
		
		logger.debug("주문 시작");
		
		IntmBas product = productService.getProductById(productId);
		
		model.addAttribute("product", product);

	    return "content/order";
	}
	
}
