package com.team.mztelecom.controller;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team.mztelecom.domain.IntmImg;
import com.team.mztelecom.domain.PurRevAttachment;
import com.team.mztelecom.service.AttachmentService;
import com.team.mztelecom.service.ProductService;

import ch.qos.logback.core.model.Model;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImgPathController {
	
	private static final Logger logger = LoggerFactory.getLogger(ImgPathController.class);

	private final AttachmentService attachmentService;
	
	private final ProductService productService;	
	
	/**
	 * 이미지 불러오기 - 김시우
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/files/{id}")
	@ResponseBody
	public Resource attachment(@PathVariable("id") Long id, Model model) throws IOException {
		logger.debug("id :: " + id);
		Optional<PurRevAttachment> outPurRevAttachmentDTO = attachmentService.findById(id);
		logger.debug("outDTO :: " + outPurRevAttachmentDTO.get().getFilePath());
		return new UrlResource("file:" + outPurRevAttachmentDTO.get().getFilePath());
	}
	
	/**
	 * 상품 메인 이미지 경로 - 박지윤
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/intmimg/{id}")
	@ResponseBody
	public Resource img(@PathVariable Long id, Model model) throws IOException {
		
		logger.debug("상품 이미지 url 컨트롤러");
		
		Optional<IntmImg> intmImgs = productService.findByIntmBas(id);
		
		return new UrlResource("file:" + intmImgs.get().getImgPath());
	}
	
	/**
	 * 상품 상세 이미지 경로 - 박지윤
	 * 
	 * @param id
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/intmDetailimg/{id}")
	@ResponseBody
	public Resource imgDetail(@PathVariable Long id, Model model) throws IOException {
		
		logger.debug("상품 이미지 url 컨트롤러");
		
		Optional<IntmImg> intmImgs = productService.findByIntmBas(id);
		
		return new UrlResource("file:" + intmImgs.get().getImgDetailPath());
	}
	
}
