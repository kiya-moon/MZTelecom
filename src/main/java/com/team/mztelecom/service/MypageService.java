package com.team.mztelecom.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.CustBas;
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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MypageService {

	private static final Logger logger = LoggerFactory.getLogger(MypageService.class);
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final ProductService productService;

	private final CustRepository custRepository;
	
	private final CustWishRepository custWishRepository;
	
	private final ProductRepository productRepository;
	
	/**
	 * 회원정보 수정 - 김시우
	 * 
	 * @param inCustBasDTO
	 */
	public void updateCustInfo(CustBasDTO inCustBasDTO) {
		
		CustBas inCustBas = inCustBasDTO.toEntity(); 
		
		List<CustBas> outCustList = custRepository.findByDynamicQuery(inCustBas.getCustId(), inCustBas.getCustNm(), 
				inCustBas.getCustBirth(), inCustBas.getCustEmail());
		
		// 기존 회원 정보
		CustBas outCustBas = outCustList.get(0);
		logger.debug("outCustBas 업데이트 전 ::: " + StringUtil.toString(outCustBas));
		
		// 비밀번호 업데이트
		if(!Utiles.isNullOrEmpty(inCustBasDTO.getCustPassword())) 
		{
			outCustBas.UpdatecustPassword(bCryptPasswordEncoder.encode(inCustBasDTO.getCustPassword()));
		}
		
		// 전화번호 업데이트
		if(!Utiles.isNullOrEmpty(inCustBasDTO.getCustNo())) 
		{
			outCustBas.UpdatecustNo(inCustBasDTO.getCustNo());
		}
		
		logger.debug("outCustBas 업데이트 후 ::: " + StringUtil.toString(outCustBas));
		
		custRepository.save(outCustBas);
		
	}
	
	/**
	 * 회원탈퇴 - 김시우
	 * 
	 * @param inCustBasDTO
	 */
	public void deleteCust(CustBasDTO inCustBasDTO) {
		
		CustBas incustBas = inCustBasDTO.toEntity();
		
		List<CustBas> outCustList = custRepository.findByDynamicQuery(incustBas.getCustId(), incustBas.getCustNm(),
				incustBas.getCustBirth(), incustBas.getCustEmail());
		
		custRepository.deleteById(outCustList.get(0).getId());
		
	}
	
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
		
		// 고객이 찜한 상품 존재o
		if(!Utiles.isNullOrEmpty(intmIdList)) 
		{
			for(int i = 0; i < intmIdList.size(); i++) 
			{
				// 고객의 찜한 상품 아이디로 상품 검색 후 상품 목록에 저장
				intmList.add(productRepository.getIntmBas(intmIdList.get(i)));
				logger.debug("intmList ::: " + intmList.get(i).getIntmKorNm());
				
			}
		}
		
		List<IntmBasDTO> intmDTOList = new ArrayList<>();
		
		// 찜한 상품 목록 DTO 변환
		for (IntmBas intmBas : intmList) 
		{
			
			// 찜한 상품 이미지
			List<IntmImg> intmImgList = intmBas.getIntmImgs();
			List<IntmImgDTO> intmImgDTOList = productService.IntmImgListToDTO(intmImgList);
			
			logger.debug("intmImgDTOList :: " + StringUtil.toString(intmImgDTOList));
			
			IntmBasDTO intmDTO = IntmBasDTO.builder()
					.id(intmBas.getId())
					.intmModelColor(intmBas.getIntmModelColor())
					.intmNm(intmBas.getIntmNm())
					.intmKorNm(intmBas.getIntmKorNm())
					.intmGB(intmBas.getIntmGB())
					.intmPrice(intmBas.getIntmPrice())
					.wishCnt(intmBas.getWishCnt())
					.fee(intmBas.getFee())
					.intmImgs(intmImgDTOList)
					.build();
			
			intmDTOList.add(intmDTO);
			
		}
		
		return intmDTOList;
	}
	
	
	
	
	
	
	
	
}
