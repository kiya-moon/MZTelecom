package com.team.mztelecom.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.SysCdBas;
import com.team.mztelecom.dto.SysCdDTO;
import com.team.mztelecom.repository.SysCdRepository;
import com.team.util.Utiles;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SysCdBasService {
	
	private static final Logger logger = LoggerFactory.getLogger(SysCdBasService.class);
	
	private final SysCdRepository sysCdRepository;
	
	/**
	 * 시스템 코드 조회 - 김시우
	 * 참고 - 기능 오픈이 정해지지 않았을 때 등 미노출 하기 위한 시스템 관리 코드
	 * 
	 * @return
	 */
	public String getSysCd(SysCdDTO inSysCdDTO) {
		
		LocalDateTime now = LocalDateTime.now(); // 현재 날짜
		String sysCdYn = "N";	// 시스템코드YN
		
		SysCdBas inSysCdBas = inSysCdDTO.toEntity();
		
		SysCdBas outSysCdBas = sysCdRepository.getSysCd(inSysCdBas.getSys_cd_group_id(), inSysCdBas.getSys_cd_id());
		
		if(!Utiles.isNullOrEmpty(outSysCdBas))
		{	
			// 현재 일자가 시작일자가 미래이거나 동일할 때
			if(now.isAfter(outSysCdBas.getEfct_st_date()) ||  now.isEqual(outSysCdBas.getEfct_st_date()))
			{
				
				logger.debug("now :: " + now);
				logger.debug("Efct_st_date :: " + outSysCdBas.getEfct_st_date());
				
				// 만료일보다 현재일자가 과거 일때
				if(now.isBefore(outSysCdBas.getEfct_fns_date()))
				{
					logger.debug("now :: " + now);
					logger.debug("Efct_fns_date :: " + outSysCdBas.getEfct_fns_date());
					sysCdYn = "Y";	// 
				}
			}
		}
		
		logger.debug("sysCdYn ::: " + sysCdYn);
		
		return sysCdYn;
	}
	
	
}
