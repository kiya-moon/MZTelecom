package com.team.mztelecom.controller;

import java.security.Principal;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;

import com.team.mztelecom.dto.CartItemDTO;
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.service.CartService;
import com.team.util.StringUtil;
import com.team.util.Utiles;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CartController {

	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	private final CartService cartService;

	@PostMapping(value = "/cart/add")
    public @ResponseBody ResponseEntity cart(@RequestBody CartItemDTO cartItemDTO, Principal principal){
		
		logger.debug("장바구니 시작");
		
		if (Utiles.isNullOrEmpty(principal)) {
	        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	    }
		
        String custId = principal.getName();
        
        logger.debug("custId 확인 ::: " + custId);
        
        Long cartItemId;
        
        logger.debug("cartItemDTO ::: " + StringUtil.toString(cartItemDTO));
        
        try {
        	cartItemId = cartService.addCart(cartItemDTO, custId);
            
        } catch(Exception e){
        	
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
	
	@GetMapping(value = "/cart")
	public String getCart(Principal principal, Model model) {
		List<IntmBasDTO> cartItemList  = cartService.getCart(principal.getName());
		
		model.addAttribute("cartItems", cartItemList);
		
		return "content/cart"; 
	}
}