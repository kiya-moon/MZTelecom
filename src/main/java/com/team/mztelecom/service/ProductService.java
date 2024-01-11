package com.team.mztelecom.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.domain.IntmImg;
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.dto.IntmImgDTO;
import com.team.mztelecom.repository.ProductRepository;
import com.team.util.StringUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	private final ProductRepository productRepository;
	
	// 상품
	public List<IntmBasDTO> getAllProductsWithImages() {
		
	    logger.debug("product 서비스");

	    List<IntmBas> intmBasList = productRepository.findAllProductsWithImages();
	    
	    List<IntmBasDTO> intmDTOList = new ArrayList<>();

	    for (IntmBas intmBas : intmBasList) {
	    	
	    	List<IntmImg> intmImgList = intmBas.getIntmImgs();
	    	List<IntmImgDTO> intmImgDTOList = IntmImgListToDTO(intmImgList);
	    	
	        logger.debug("intmImgDTOList :: " + StringUtil.toString(intmImgDTOList));

	        IntmBasDTO intmDTO = new IntmBasDTO(
	            intmBas.getId(), 
	            intmBas.getIntmModelColor(), 
	            intmBas.getIntmNm(),
	            intmBas.getIntmKorNm(), 
	            intmBas.getIntmGB(),
	            intmBas.getIntmPrice(), 
	            intmBas.isLiked(),
	            intmImgDTOList
	        );

	        intmDTOList.add(intmDTO);
	        
	    }
	    
	    
	    logger.debug("intmDTOList :: " + StringUtil.toString(intmDTOList));
	    
	    return intmDTOList;
	}
	
	// 상품 이미지
	private List<IntmImgDTO> IntmImgListToDTO(List<IntmImg> intmImgList) {
		
	    List<IntmImgDTO> intmImgDTOList = new ArrayList<>();
	    
	    for (IntmImg intmImg : intmImgList) {
	    	
	    	IntmImgDTO intmImgDTO = new IntmImgDTO(
	                intmImg.getId(), 
	                intmImg.getIntmNm(),
	                intmImg.getImgName(),
	                intmImg.getImgPath()
	        );
	    	
	        intmImgDTOList.add(intmImgDTO);
	    }
	    return intmImgDTOList;
	}


	// 상품상세 url
	public IntmBas getProductById(Long productId) {

		return productRepository.findById(productId).orElse(null);
	}
	
	
	// 찜하기
	public boolean toggleProductLiked(Long productId ,IntmBasDTO inIntmBasDTO) {
		
        IntmBas productItem = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        
        logger.debug("productItem :: " + StringUtil.toString(productItem));
        
        boolean chgLiked = false;
        
        if(!productItem.isLiked()) {
        	chgLiked = true;
        }
        
        List<IntmImg> intmImgList = productItem.getIntmImgs();
        List<IntmImgDTO> intmImgDTOList = IntmImgListToDTO(intmImgList);
        
        productItem.UpdateLiked(chgLiked);

        productRepository.save(productItem);
        
        Optional<IntmBas> outIntmBas = productRepository.findById(productItem.getId());
        
        IntmBasDTO productDTO = IntmBasDTO.builder()
                .id(outIntmBas.get().getId())
                .intmModelColor(outIntmBas.get().getIntmModelColor())
                .intmNm(outIntmBas.get().getIntmNm())
                .intmKorNm(outIntmBas.get().getIntmKorNm())
                .intmGB(outIntmBas.get().getIntmGB())
                .intmPrice(outIntmBas.get().getIntmPrice())
                .intmImgs(intmImgDTOList)
                .isLiked(outIntmBas.get().isLiked())
                .build();

        return productDTO.isLiked();
    }
	
}
