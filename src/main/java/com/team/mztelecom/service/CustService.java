package com.team.mztelecom.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.dto.CustBasSaveDTO;
import com.team.mztelecom.repository.CustRepository;
import com.team.util.StringUtil;

@Service
public class CustService {

	private static final Logger logger = LoggerFactory.getLogger(CustService.class);
	
	@Autowired
	CustRepository custRepository;
	
	public void findId(Map<String, String> temp) {
		
		logger.debug("서비스 진입");
		logger.debug("temp 확인 ::" + temp.toString());

		// temp 맵의 값들을 사용하여 CustBasSaveDTO의 새로운 인스턴스 생성
        CustBasSaveDTO custBasSaveDTO = CustBasSaveDTO.builder()
                .custNm(temp.get("custNm"))
                .custBirth(temp.get("birth"))
                .custEmail(temp.get("email"))
                .build();

        // DTO를 CustBas 엔터티로 변환
        CustBas custBasEntity = custBasSaveDTO.toEntity();
        
        logger.debug(StringUtil.toString(custBasEntity));

	}
}
