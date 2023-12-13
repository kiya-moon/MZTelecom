package com.team.mztelecom.service;

import java.util.*;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
	            intmBas.getRepIntmModelId(),
	            intmBas.getIntmModelColor(), 
	            intmBas.getIntmSeq(), 
	            intmBas.getIntmIdfyNo(), 
	            intmBas.getIntmNm(),
	            intmBas.getIntmKorNm(), 
	            intmBas.getIntmGB(), 
	            intmBas.getIntmPrice(), 
	            intmBas.getIntmSalesStatus(),
	            intmBas.getIntmBuyerId(), 
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
	                intmImg.getRepIntmModelId(),
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
	public void toggleProductLiked(Long productId) {
		
        IntmBas productItem = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        
        List<IntmImg> intmImgList = productItem.getIntmImgs();
        List<IntmImgDTO> intmImgDTOList = IntmImgListToDTO(intmImgList);
        
        IntmBasDTO productDTO = IntmBasDTO.builder()
                .id(productItem.getId())
                .repIntmModelId(productItem.getRepIntmModelId())
                .intmModelColor(productItem.getIntmModelColor())
                .intmSeq(productItem.getIntmSeq())
                .intmIdfyNo(productItem.getIntmIdfyNo())
                .intmNm(productItem.getIntmNm())
                .intmKorNm(productItem.getIntmKorNm())
                .intmGB(productItem.getIntmGB())
                .intmPrice(productItem.getIntmPrice())
                .intmSalesStatus(productItem.getIntmSalesStatus())
                .intmBuyerId(productItem.getIntmBuyerId())
                .intmImgs(intmImgDTOList)
                .isLiked(productItem.isLiked())
                .build();

//        productRepository.save(productDTO.toEntity());
        
        IntmBas savedProduct = productRepository.save(productDTO.toEntity());
        logger.debug("savedProduct :: " + StringUtil.toString(savedProduct));
    }

}
