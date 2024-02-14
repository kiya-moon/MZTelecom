package com.team.mztelecom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.team.mztelecom.dto.TemporarySaveDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SetIntmModelIdService {
	
	private static final Logger logger = LoggerFactory.getLogger(SetIntmModelIdService.class);	
	
	private final TemporarySaveDTO temporarySaveDTO;

	
	/**
	 * 임시 저장된 주문 정보를 바탕으로 상품 모델 ID를 설정 - 박지윤
	 * 
	 */
	public void setIntmProduct() {
		
		String intmKorNm = temporarySaveDTO.getOrderTmp().get(0);
		String cliColor = temporarySaveDTO.getOrderTmp().get(1);
		String cliCpcty = temporarySaveDTO.getOrderTmp().get(2);
		
		String intmModelId="";
		
		
		// 상품 이름과 색상, 용량에 따라 상품 모델 ID를 결정
		if(intmKorNm.equals("갤럭시Z플립5")) {
			
			if(cliColor.equals("black")) {
				
				if(cliCpcty.equals("128GB")) {
					intmModelId = "K703031";	// 검정, 128 
				} else {
					intmModelId = "K703032"; 	// 검정 , 512
				}
				
			} else { // 민트
				if(cliCpcty.equals("128GB")) {
					intmModelId = "K703033";	// 민트, 128 
				} else {
					intmModelId = "K703034"; 	// 민트 , 512
				}
			}
			
		} else if(intmKorNm.equals("갤럭시S23_Ultra")) {
			if(cliColor.equals("black")) {
				if(cliCpcty.equals("128GB")) {
					intmModelId = "K703041";	// 검정, 128 
				} else {
					intmModelId = "K703042"; 	// 검정 , 512
				}
			} else {	// 그린
				if(cliCpcty.equals("128GB")) {
					intmModelId = "K703041";	// 그린, 128 
				} else {
					intmModelId = "K703042"; 	// 그린 , 512
				}
			}
		}
		logger.debug("intmModelId :: " + intmModelId);
		temporarySaveDTO.setTmpIntmModelId(intmModelId);
	}
}
