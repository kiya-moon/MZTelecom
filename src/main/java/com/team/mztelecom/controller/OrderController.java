package com.team.mztelecom.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.team.mztelecom.dto.TemporarySaveDTO;

import lombok.RequiredArgsConstructor;

@Controller
public class OrderController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	TemporarySaveDTO temporarySaveDTO;
	
	@GetMapping(value = "/order")
	public String order(Locale locale, Model model) {
		
		String intmKorNm = temporarySaveDTO.getOrderTmp().get(0);
		String cliColor = temporarySaveDTO.getOrderTmp().get(1);
		String cliCpcty = temporarySaveDTO.getOrderTmp().get(2);
		String ChName = temporarySaveDTO.getOrderTmp().get(3);
		String AllPhone = temporarySaveDTO.getOrderTmp().get(4);
		String ChZip = temporarySaveDTO.getOrderTmp().get(5);
		String ChAddress1 = temporarySaveDTO.getOrderTmp().get(6);
		String ChAddress2 = temporarySaveDTO.getOrderTmp().get(7);
		
		model.addAttribute("intmKorNm", intmKorNm);
		model.addAttribute("cliColor", cliColor);
		model.addAttribute("cliCpcty", cliCpcty);
		model.addAttribute("ChName", ChName);
		model.addAttribute("AllPhone", AllPhone);
		model.addAttribute("ChZip", ChZip);
		model.addAttribute("ChAddress1", ChAddress1);
		model.addAttribute("ChAddress2", ChAddress2);
		
		return "content/order";
	}
	
	@PostMapping(value = "/postOrder")
	public String order(Model model, 
			@RequestParam("ChName") String ChName,
			@RequestParam("AllPhone") String AllPhone,
			@RequestParam("ChZip") String ChZip,
			@RequestParam("ChAddress1") String ChAddress1,
			@RequestParam("ChAddress2") String ChAddress2 ) {
		
		logger.debug("결제 시작");

		temporarySaveDTO.getOrderTmp().add(ChName);
		temporarySaveDTO.getOrderTmp().add(AllPhone);
		temporarySaveDTO.getOrderTmp().add(ChZip);
		temporarySaveDTO.getOrderTmp().add(ChAddress1);
		temporarySaveDTO.getOrderTmp().add(ChAddress2);
		
		return "content/order";
	}
	
}
