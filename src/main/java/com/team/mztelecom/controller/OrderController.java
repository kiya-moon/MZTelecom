package com.team.mztelecom.controller;

import java.util.*;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team.mztelecom.dto.TemporarySaveDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class OrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	private final TemporarySaveDTO temporarySaveDTO;
	
	/**
	 * 결제 할 상품 정보 보여주기 - 박지윤
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/order")
	public String order(Locale locale, Model model) {
		
	    List<String> orderTmp = temporarySaveDTO.getOrderTmp();
	    
	    model.addAttribute("intmKorNm", orderTmp.get(0));
	    model.addAttribute("cliColor", orderTmp.get(1));
	    model.addAttribute("cliCpcty", orderTmp.get(2));
	    model.addAttribute("cliPrice", orderTmp.get(3));
	    model.addAttribute("ChName", orderTmp.get(4));
	    model.addAttribute("AllPhone", orderTmp.get(5));
	    model.addAttribute("ChZip", orderTmp.get(6));
	    model.addAttribute("ChAddress1", orderTmp.get(7));
	    model.addAttribute("ChAddress2", orderTmp.get(8));
		
		return "content/order";
	}
	
	/**
	 * 본인인증 - 박지윤
	 * 
	 * @param model
	 * @param ChName
	 * @param AllPhone
	 * @param ChZip
	 * @param ChAddress1
	 * @param ChAddress2
	 * @return
	 */
	@PostMapping(value = "/postOrder")
	public String order(Model model, 
			@RequestParam("ChName") String ChName,
			@RequestParam("AllPhone") String AllPhone,
			@RequestParam("ChZip") String ChZip,
			@RequestParam("ChAddress1") String ChAddress1,
			@RequestParam("ChAddress2") String ChAddress2 ) {
		
		temporarySaveDTO.getOrderTmp().add(ChName);
		temporarySaveDTO.getOrderTmp().add(AllPhone);
		temporarySaveDTO.getOrderTmp().add(ChZip);
		temporarySaveDTO.getOrderTmp().add(ChAddress1);
		temporarySaveDTO.getOrderTmp().add(ChAddress2);
		
		return "content/order";
	}
	
}
