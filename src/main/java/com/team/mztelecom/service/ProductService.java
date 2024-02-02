package com.team.mztelecom.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.domain.IntmImg;
import com.team.mztelecom.domain.PurRevBoard;
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.dto.IntmImgDTO;
import com.team.mztelecom.dto.PurRevBoardDTO;
import com.team.mztelecom.repository.ProductRepository;
import com.team.util.StringUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	private final ProductRepository productRepository;
	
	// 상품
	public Page<IntmBasDTO> getAllProductsWithImages(String sortBy, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, getSort(sortBy));
		
	    logger.debug("product 서비스");
	    
	    Page<IntmBas> intmBasPage = productRepository.findAll(pageable);

	    List<IntmBasDTO> intmDTOList = new ArrayList<>();

	    for (IntmBas intmBas : intmBasPage.getContent()) {
	    	
	    	List<IntmImgDTO> intmImgDTOList = IntmImgListToDTO(intmBas.getIntmImgs());
	    	
	        logger.debug("intmImgDTOList :: " + StringUtil.toString(intmImgDTOList));

	        IntmBasDTO intmDTO = new IntmBasDTO(
	            intmBas.getId(), 
	            null,
	            intmBas.getIntmModelColor(), 
	            intmBas.getIntmNm(),
	            intmBas.getIntmKorNm(), 
	            intmBas.getIntmGB(),
	            intmBas.getIntmPrice(), 
	            intmBas.getWishCnt(),
	            intmImgDTOList,
	            intmBas.getFee(),
	            intmBas.getCreatedAt()
	        );

	        intmDTOList.add(intmDTO);
	        
	    }
	    
	    logger.debug("intmDTOList :: " + StringUtil.toString(intmDTOList));
	    
	    return new PageImpl<>(intmDTOList, pageable, intmBasPage.getTotalElements());
	}
	
	// 상품 이미지
	public List<IntmImgDTO> IntmImgListToDTO(List<IntmImg> intmImgList) {
		
	    List<IntmImgDTO> intmImgDTOList = new ArrayList<>();
	    
	    for (IntmImg intmImg : intmImgList) {
	    	
	    	IntmImgDTO intmImgDTO = new IntmImgDTO(
	                intmImg.getId(), 
	                intmImg.getIntmNm(),
	                intmImg.getImgDetailNm(),
	                intmImg.getImgName(),
	                intmImg.getImgPath(),
	                intmImg.getImgDetailPath()
	        );
	    	
	        intmImgDTOList.add(intmImgDTO);
	    }
	    return intmImgDTOList;
	}


	// 상품상세 url
	public IntmBas getProductById(Long productId) {

		return productRepository.findById(productId).orElse(null);
	}
	
	
	private Sort getSort(String sortBy) {
        switch (sortBy) {
            case "createdAt":
                return Sort.by(Sort.Direction.DESC, "createdAt");
            case "intmPrice":
                return Sort.by(Sort.Direction.ASC, "intmPrice");
            case "intmPriceDesc":
                return Sort.by(Sort.Direction.DESC, "intmPrice");
            default:
                return Sort.by(Sort.Direction.DESC, "createdAt");
        }
    }
}
