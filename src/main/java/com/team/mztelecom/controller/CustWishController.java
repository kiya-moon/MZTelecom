package com.team.mztelecom.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.team.mztelecom.service.CustWishService;
import com.team.mztelecom.service.CustService;
import com.team.util.Utiles;

@Controller
public class CustWishController {

	private static final Logger logger = LoggerFactory.getLogger(CustWishController.class);
	
	@Autowired
	CustWishService custWishService;
	
	@Autowired
	CustService custService;
	
	/**
	 * 찜하기 기능
	 * 
	 * @param productId
	 * @param model
	 * @param principal
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/product/{productId}/liked")
	public ResponseEntity likeChk(@PathVariable Long productId, Model model, Principal principal) {
	
		Map<String, Boolean> response = new HashMap<>();
		
		if(!Utiles.isNullOrEmpty(principal)) {
			boolean isLiked;						// 찜하기 유무
			String custId = principal.getName();	// 로그인 한 고객아이디
			int wishNum =  custWishService.wishChk(productId, custId); // 해당 고객이 해당 상품을 찜하기 했을 경우 : 1 , 아닐경우  : 0
			
			logger.debug("likeChk ::: " + wishNum);
			
			// 기존에 찜하기가 되어 있을 경우
			if(wishNum > 0) 
			{
				custWishService.reduceWish(productId);
				custWishService.removeWish(custId, productId);
				isLiked = false;
				logger.debug("찜하기 취소");
			}
			// 기존에 찜하기가 안 되어 있을 경우 
			else	 
			{	
				custWishService.increaseWish(productId);
				custWishService.saveWish(custId, productId);
				isLiked = true;
				logger.debug("찜하기");
				
			}
			
			// 찜하기 유무
			response.put("isLiked", isLiked);
		}
		
	    response.put("isLoggedIn", custService.isLoggedIn());

	    return ResponseEntity.ok(response);
	}
}
