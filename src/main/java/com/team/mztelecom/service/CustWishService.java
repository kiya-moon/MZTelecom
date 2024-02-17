package com.team.mztelecom.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.CustWish;
import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.dto.CustBasDTO;
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.repository.CustWishRepository;
import com.team.mztelecom.repository.CustRepository;
import com.team.mztelecom.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustWishService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustWishService.class);
	
	private final CustRepository custRepository;
	
	private final CustWishRepository custWishRepository;
	
	private final ProductRepository productRepository;
	
	/**
	 * 찜하기 저장처리 - 김시우
	 * 
	 * @param custId
	 * @param productId
	 */
	public void saveWish(String custId, Long productId) {
		
		logger.debug("저장처리");
		
		// 고객데이터
		CustBasDTO inCustDTO = CustBasDTO.builder()
							.custId(custId)
							.build();
		
		CustBas inCustEntity = inCustDTO.toEntity();
		
		List<CustBas> outCustBas = 
				custRepository.findByDynamicQuery(inCustEntity.getCustId(), inCustEntity.getCustNm(), inCustEntity.getCustBirth(), inCustEntity.getCustEmail());
		
		CustBas outCustEntity = CustBas.builder()
				.id(outCustBas.get(0).getId())
				.custId(outCustBas.get(0).getCustId())
				.custNm(outCustBas.get(0).getCustNm())
				.custPassword(outCustBas.get(0).getCustPassword())
				.custIdfyNo(outCustBas.get(0).getCustIdfyNo())
				.custBirth(outCustBas.get(0).getCustBirth())
				.custNo(outCustBas.get(0).getCustNo())
				.custSex(outCustBas.get(0).getCustSex())
				.custAddress(outCustBas.get(0).getCustAddress())
				.custEmail(outCustBas.get(0).getCustEmail())
				.build();
		
		// 상품데이터 조회
		IntmBasDTO inIntmDTO = IntmBasDTO.builder()
								.id(productId)
								.build();
		
		IntmBas inIntmEntity = inIntmDTO.toEntity();
		
		Optional<IntmBas> outIntmBas = productRepository.findById(inIntmEntity.getId());
		
		IntmBas outIntmEntity = IntmBas.builder()
									.id(outIntmBas.get().getId())
									.intmModelColor(outIntmBas.get().getIntmModelColor())
									.intmNm(outIntmBas.get().getIntmNm())
									.intmKorNm(outIntmBas.get().getIntmKorNm())
									.intmGB(outIntmBas.get().getIntmGB())
									.intmPrice(outIntmBas.get().getIntmPrice())
									.intmImgs(outIntmBas.get().getIntmImgs())
									.wishCnt(outIntmBas.get().getWishCnt())
									.build();
		
		// 저장
		CustWish incuLike = CustWish.builder()
							.custBas(outCustEntity)
							.intmBas(outIntmEntity)
							.build();
		
		custWishRepository.save(incuLike);
		
		logger.debug("찜하기 저장 완료");
	}
	
	/**
	 * 찜하기 취소 처리 - 김시우
	 * 
	 * @param custId
	 * @param productId
	 */
	public void removeWish(String custId, Long productId) {
		
		// 고객데이터
		CustBasDTO inCustDTO = CustBasDTO.builder()
							.custId(custId)
							.build();
		
		CustBas inCustEntity = inCustDTO.toEntity();
		
		// 고객 id 조회
		List<CustBas> outCustBas  
			= custRepository.findByDynamicQuery(inCustEntity.getCustId(), inCustEntity.getCustNm(), inCustEntity.getCustBirth(), inCustEntity.getCustEmail());
		
		IntmBasDTO inIntmDTO = IntmBasDTO.builder()
							.id(productId)
							.build();
		
		IntmBas inIntm = inIntmDTO.toEntity();
		
		// 상품 데이터
		Optional<IntmBas> inIntmBas = productRepository.findById(inIntm.getId());
		
		// 찜하기 취소
		custWishRepository.deleteByCustandIntm(inIntmBas.get().getId(), outCustBas.get(0).getId());
		
	}
	
	/**
	 * 찜하기 유무 확인 - 김시우
	 * 
	 * @param inProductId
	 * @param inCustId
	 * @return
	 */
	public int wishChk(Long inProductId, String inCustId) {
		
		int wishCnt;
		logger.debug("inCustId 레파지토리 전 :: " + inCustId);
		
		CustBasDTO inCustDTO = CustBasDTO.builder()
								.custId(inCustId)
								.build();
		
		IntmBasDTO inIntmDTO = IntmBasDTO.builder()
								.id(inProductId)
								.build();
		
		IntmBas outIntmEntity = inIntmDTO.toEntity();
		
		// 상품 id 조회
		Optional<IntmBas> inIntmBas = productRepository.findById(outIntmEntity.getId());
		
		CustBas outCustEntity = inCustDTO.toEntity();
		
		// 고객 id 조회
		List<CustBas> inCustBas  = custRepository.findByDynamicQuery(outCustEntity.getCustId(), outCustEntity.getCustNm(), outCustEntity.getCustBirth(), outCustEntity.getCustEmail());
		
		// // 찜하기 체크 확인
		wishCnt = custWishRepository.wishChk(inIntmBas.get().getId(), inCustBas.get(0).getId());
		
		return wishCnt;
	}
	
	/**
	 * 찜하기 갯수 수정(증가) - 김시우
	 * 
	 * @param productId
	 */
	public void increaseWish(Long inProductId) {
		
		IntmBasDTO inDTO = IntmBasDTO.builder()
				.id(inProductId)
				.build();
		
		IntmBas inIntm = inDTO.toEntity();
		
		custWishRepository.increaseLike(inIntm.getId());
	}
	
	/**
	 * 찜하기 갯수 수정(감소) - 김시우
	 * 
	 * @param productId
	 */
	public void reduceWish(Long inProductId) {
		
		IntmBasDTO inDTO = IntmBasDTO.builder()
				.id(inProductId)
				.build();
		
		IntmBas inIntm = inDTO.toEntity();
		
		custWishRepository.reduceLike(inIntm.getId());
	}
	
	/**
	 * 찜하기 조회 - 김시우
	 * 
	 * @param inIntmId
	 * @param inCustId
	 * @return
	 */
	public int getWish(Long inIntmId, String inCustId) {
		
		// 고객데이터
		CustBasDTO inCustDTO = CustBasDTO.builder()
							.custId(inCustId)
							.build();
		
		CustBas inCustEntity = inCustDTO.toEntity();
		
		List<CustBas> outCustBas = 
				custRepository.findByDynamicQuery(inCustEntity.getCustId(), inCustEntity.getCustNm(), inCustEntity.getCustBirth(), inCustEntity.getCustEmail());
		
		
		// 상품데이터 조회
		IntmBasDTO inIntmDTO = IntmBasDTO.builder()
								.id(inIntmId)
								.build();
		
		IntmBas inIntmEntity = inIntmDTO.toEntity();
		
		Optional<IntmBas> outIntmBas = productRepository.findById(inIntmEntity.getId());
		
		
		int wishCnt = custWishRepository.wishChk(outIntmBas.get().getId(), outCustBas.get(0).getId());
		
		
		return wishCnt;
	}
	
}
