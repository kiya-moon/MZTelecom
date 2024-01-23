package com.team.mztelecom.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.CustWish;
import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.dto.CustBasDTO;
import com.team.mztelecom.repository.CustWishRepository;
import com.team.mztelecom.repository.CustRepository;
import com.team.mztelecom.repository.ProductRepository;

@Service
public class CustWishService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustWishService.class);
	
	@Autowired
	CustService custService;
	
	@Autowired
	CustRepository custRepository;
	
	@Autowired
	CustWishRepository custWishRepository;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductRepository productRepository;
	
	/**
	 * 찜하기 저장처리 - 김시우
	 * 
	 * @param custId
	 * @param productId
	 */
	public void saveWish(String custId, Long productId) {
		
		logger.debug("저장처리");
		
		// 고객데이터
		CustBas inCustEntity = CustBas.builder()
							.custId(custId)
							.build();
		
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
				.intmPurStusYn(outCustBas.get(0).getIntmPurStusYn())
				.build();
		
		// 상품데이터 조회
		IntmBas inIntmEntity = IntmBas.builder()
								.id(productId)
								.build();
		
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
		CustBasDTO inDTO = CustBasDTO.builder()
							.custId(custId)
							.build();
		
		CustBas inCustEntity = CustBas.builder()
							.custId(inDTO.getCustId())
							.build();
		
		// 고객 id 조회
		List<CustBas> outCustBas  
			= custRepository.findByDynamicQuery(inCustEntity.getCustId(), inCustEntity.getCustNm(), inCustEntity.getCustBirth(), inCustEntity.getCustEmail());
		
		IntmBas outIntm = IntmBas.builder()
							.id(productId)
							.build();
		
		// 상품 데이터
		Optional<IntmBas> inIntmBas = productRepository.findById(outIntm.getId());
		
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
		
		IntmBas outIntmEntity = IntmBas.builder()
				.id(inProductId)
				.build();
		
		// 상품 id 조회
		Optional<IntmBas> inIntmBas = productRepository.findById(outIntmEntity.getId());
		
		CustBas outCustEntity = CustBas.builder()
				.custId(inCustId)
				.build();
		
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
		
		IntmBas inIntmEntity = IntmBas.builder()
				.id(inProductId)
				.build();
		
		custWishRepository.increaseLike(inIntmEntity.getId());
	}
	
	/**
	 * 찜하기 갯수 수정(감소) - 김시우
	 * 
	 * @param productId
	 */
	public void reduceWish(Long inProductId) {
		
		IntmBas inIntmEntity = IntmBas.builder()
				.id(inProductId)
				.build();
		
		custWishRepository.reduceLike(inIntmEntity.getId());
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
		CustBas inCustEntity = CustBas.builder()
							.custId(inCustId)
							.build();
		
		List<CustBas> outCustBas = 
				custRepository.findByDynamicQuery(inCustEntity.getCustId(), inCustEntity.getCustNm(), inCustEntity.getCustBirth(), inCustEntity.getCustEmail());
		
		
		// 상품데이터 조회
		IntmBas inIntmEntity = IntmBas.builder()
								.id(inIntmId)
								.build();
		
		Optional<IntmBas> outIntmBas = productRepository.findById(inIntmEntity.getId());
		
		
		int wishCnt = custWishRepository.wishChk(outIntmBas.get().getId(), outCustBas.get(0).getId());
		
		
		return wishCnt;
	}
	
}
