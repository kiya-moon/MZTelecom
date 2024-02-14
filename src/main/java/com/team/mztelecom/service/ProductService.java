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
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.dto.IntmImgDTO;
import com.team.mztelecom.repository.ImgRepository;
import com.team.mztelecom.repository.ProductRepository;
import com.team.util.StringUtil;
import com.team.util.Utiles;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	private final ProductRepository productRepository;

	private final ImgRepository imgRepository;
	
	/**
	 * 구매후기 카테고리용 상품 조회 - 김시우
	 * 
	 * @return
	 */
	public List<IntmBasDTO> getAllIntmList(){
		
		List<IntmBasDTO> outDTOList = new ArrayList<>();
		
		List<IntmBas> outList = productRepository.findAll();
		
		// 상품 조회 후 상품이 있을 경우 상품 한글명 세팅
		if(!Utiles.isNullOrEmpty(outList)) 
		{
			for(IntmBas outEntity : outList) 
			{
				IntmBasDTO inDTO = IntmBasDTO.builder()
						.id(outEntity.getId())
						.intmKorNm(outEntity.getIntmKorNm())
						.build();
				
				outDTOList.add(inDTO);
			}
		}
		
		return outDTOList;
	}
	
	/**
	 * 페이지네이션, 상품, 상품 이미지 - 박지윤
	 * 
	 * @param sortBy
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<IntmBasDTO> getAllProductsWithImages(String sortBy, int page, int size) {
		
		// 페이지 및 정렬 정보 생성
        Pageable pageable = PageRequest.of(page, size, getSort(sortBy));
		
	    logger.debug("product 서비스");
	    
	    // 모든 상품을 페이지네이션해서 가져오기
	    Page<IntmBas> intmBasPage = productRepository.findAll(pageable);
	    
	    // 상품 목록을 담을 리스트 생성
	    List<IntmBasDTO> intmDTOList = new ArrayList<>();
	    
	    // 가져온 상품들을 반복, DTO로 변환
	    for (IntmBas intmBas : intmBasPage.getContent()) {
	    	
	    	List<IntmImgDTO> intmImgDTOList = IntmImgListToDTO(intmBas.getIntmImgs());
	    	
	        logger.debug("intmImgDTOList :: " + StringUtil.toString(intmImgDTOList));
	        
	        // 상품 DTO 생성 리스트에 추가
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
	    
	    // DTO 리스트와 페이지 정보를 포함한 페이지 객체 반환
	    return new PageImpl<>(intmDTOList, pageable, intmBasPage.getTotalElements());
	}
	
	/**
	 * 상품 이미지 - 박지윤
	 * 
	 * @param intmImgList
	 * @return
	 */
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
	
	/**
	 * 상품 이미지 조회 - 박지윤
	 * 
	 * @param id
	 * @return
	 */
	public Optional<IntmImg> findByIntmBas(Long id) {
		return imgRepository.findByIntmBasId(id);
	}

	/**
	 * 상품 상세 - 박지윤
	 * 
	 * @param productId
	 * @return
	 */
	public IntmBasDTO getProductById(Long productId) {
		logger.debug("productId :::  " + productId);
		
		IntmBasDTO inDTO = IntmBasDTO.builder()
							.id(productId)
							.build();
		
		logger.debug("inDTO :: " + inDTO.getId());
		
		IntmBas inEntity = inDTO.toEntity();
		
		logger.debug("inEntity :: " + inEntity.getId());
		
		// 상품 ID로 상품 엔티티 조회
		IntmBas outEntity = productRepository.findById(inEntity.getId()).orElse(null);
		
		// 상품에 연결된 이미지를 조회
		IntmImg outImg = imgRepository.findByIntmBasId(outEntity.getId()).orElse(null);
		
		// 조회된 이미지 DTO로 변환
		IntmImgDTO outImgDTO = IntmImgDTO.builder()
								.id(outImg.getId())
								.imgName(outImg.getImgName())
								.imgPath(outImg.getImgPath())
								.imgDetailNm(outImg.getImgDetailNm())
								.imgDetailPath(outImg.getImgDetailPath())
								.build();
		
		logger.debug("images ::: " + outEntity.getIntmImgs().get(0));
		
		List<IntmImgDTO> imgList = new ArrayList<>();
		imgList.add(outImgDTO);

		IntmBasDTO outDTO = IntmBasDTO.builder()
								.id(outEntity.getId())
								.intmGB(outEntity.getIntmGB())
								.intmModelColor(outEntity.getIntmModelColor())
								.intmKorNm(outEntity.getIntmKorNm())
								.intmNm(outEntity.getIntmNm())
								.intmPrice(outEntity.getIntmPrice())
								.fee(outEntity.getFee())
								.wishCnt(outEntity.getWishCnt())
								.createdAt(outEntity.getCreatedAt())
								.intmImgs(imgList)
								.build();
		
		return outDTO;
	}
	
	/**
	 * 상품 정렬 - 박지윤
	 * 
	 * @param sortBy
	 * @return
	 */
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
