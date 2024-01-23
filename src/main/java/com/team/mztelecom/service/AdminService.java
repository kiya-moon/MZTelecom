package com.team.mztelecom.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.dto.InquiryCustDTO;
import com.team.mztelecom.repository.AdminRepository;
import com.team.util.StringUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	private final AdminRepository adminRepository;

	// 1. 사용자 정보 담기
	public List<InquiryCustDTO> getCustInfoList() {
		logger.debug("Admin 서비스");

		List<CustBas> custBasList = adminRepository.findAllCustInfo();
		List<InquiryCustDTO> inquiryCustDTO = new ArrayList<>();
		
		for(CustBas custBas : custBasList) {
			logger.debug("inquiryCustDTO :: " + StringUtil.toString(inquiryCustDTO));
			
			InquiryCustDTO result = new InquiryCustDTO(
				custBas.getCustId(),
				custBas.getCustNm(),
				custBas.getCustIdfyNo(),
				custBas.getCustBirth(),
				custBas.getCustNo(),
				custBas.getCustSex(),
				custBas.getCustAddress(),
				custBas.getCustEmail(),
				custBas.getIntmPurStusYn()
			);
			
			inquiryCustDTO.add(result);
		}
		
		logger.debug("inquiryCustDTO :: " + StringUtil.toString(inquiryCustDTO));
		
		return inquiryCustDTO;
	}

	
	// 2. 상품 정보 담기
	// 3. 문의 정보 담기
	
}
