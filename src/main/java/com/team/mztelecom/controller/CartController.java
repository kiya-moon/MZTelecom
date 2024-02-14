package com.team.mztelecom.controller;

import java.security.Principal;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import com.team.mztelecom.dto.CartItemDTO;
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.service.CartService;
import com.team.util.StringUtil;
import com.team.util.Utiles;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CartController {

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	private final CartService cartService;
	
	// 장바구니에 상품 담기
	@PostMapping(value = "/cart/add")
    public @ResponseBody ResponseEntity cart(@ModelAttribute CartItemDTO cartItemDTO, Principal principal){
		
		logger.debug("장바구니 시작");
		
		if (Utiles.isNullOrEmpty(principal)) {
			return new ResponseEntity<>("로그인 후 이용해주세요", HttpStatus.UNAUTHORIZED);
	    }
		
        String custId = principal.getName();
        
        logger.debug("custId 확인 ::: " + custId);
        
        Long cartItemId;
        
        logger.debug("cartItemDTO ::: " + StringUtil.toString(cartItemDTO));
        
        try {
        	cartItemId = cartService.addCart(cartItemDTO, custId);
        	logger.debug("장바구니 담기 성공");
            
        } catch(Exception e){
        	
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
	
	// 장바구니에 담겨있는 상품 보여주기
	@GetMapping(value = "/cart")
	public String getCart(Principal principal, Model model) {
		
		logger.debug("장바구니 컨트롤러");
		
		List<IntmBasDTO> cartItemList  = cartService.getCart(principal.getName());
		
		logger.debug("cartItemList ::: " + StringUtil.toString(cartItemList));
		
		model.addAttribute("cartItems", cartItemList);
		
		return "cart"; 
	}
	
	// 장바구니에 있는 상품 삭제
	@DeleteMapping(value = "/cart/{id}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("id") Long cartItemId, Principal principal) {
		
		logger.debug("장바구니 아이템 delete 컨트롤러");
		
		try {
			
	        cartService.deleteCartItem(cartItemId);
	        
	    } catch (Exception e) {
	    	
	        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	    
	    }
		return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
}