package com.team.mztelecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.CustWish;
import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.domain.IntmImg;
import com.team.mztelecom.dto.CustBasDTO;
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.dto.IntmImgDTO;
import com.team.mztelecom.repository.CustRepository;
import com.team.mztelecom.repository.CustWishRepository;
import com.team.mztelecom.repository.ProductRepository;
import com.team.util.StringUtil;
import com.team.util.Utiles;

@Service
public class MypageService {

	private static final Logger logger = LoggerFactory.getLogger(MypageService.class);
	
	@Autowired
	CustRepository custRepository;
	
	@Autowired
	CustWishRepository custWishRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	ProductService productService;
	
	/**
	 * 마이페이지 찜한 상품 - 김시우
	 * 
	 * @param inCustId
	 * @return
	 */
	public List<IntmBasDTO> listMyWish(String inCustId) {
		
		CustBasDTO inCustBasDTO = CustBasDTO.builder()
								.custId(inCustId)
								.build();
		
		CustBas inCustBas = inCustBasDTO.toEntity();
		
		// 로그인 한 사용자 정보 조회
		List<CustBas> custList = custRepository.findByDynamicQuery(inCustBas.getCustId(),null, null, null);
		
		// 사용자 기준으로 찜한 상품 아이디 조회
		List<Long> intmIdList = custWishRepository.getWish(custList.get(0).getId());
		
		// 찜하기 한 상품 목록
		List<IntmBas> intmList = new ArrayList<>();
		
		logger.debug("wishList.size() :: " + intmIdList.size());
		if(!Utiles.isNullOrEmpty(intmIdList)) 
		{
			for(int i = 0; i < intmIdList.size(); i++) 
			{
				
				intmList.add(productRepository.getIntmBas(intmIdList.get(i)));
				logger.debug("intmList ::: " + intmList.get(i).getIntmKorNm());
				
			}
		}
		
		List<IntmBasDTO> intmDTOList = new ArrayList<>();
		
		// 찜한 상품 목록 DTO 변환
		for (IntmBas intmBas : intmList) {
			
			// 찜한 상품 이미지
			List<IntmImg> intmImgList = intmBas.getIntmImgs();
			List<IntmImgDTO> intmImgDTOList = productService.IntmImgListToDTO(intmImgList);
			
			logger.debug("intmImgDTOList :: " + StringUtil.toString(intmImgDTOList));
			
			IntmBasDTO intmDTO = new IntmBasDTO(
					intmBas.getId(), 
					intmBas.getIntmModelColor(), 
					intmBas.getIntmNm(),
					intmBas.getIntmKorNm(), 
					intmBas.getIntmGB(),
					intmBas.getIntmPrice(), 
					intmBas.getWishCnt(),
					intmImgDTOList
					);
			
			intmDTOList.add(intmDTO);
			
		}
		
		return intmDTOList;
	}
	
	
	
	
	
	
	
	
}
