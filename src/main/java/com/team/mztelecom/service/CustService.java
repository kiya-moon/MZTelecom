package com.team.mztelecom.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.PrincipalDetails;
import com.team.mztelecom.dto.CustBasBringDTO;
import com.team.mztelecom.dto.CustBasSaveDTO;
import com.team.mztelecom.repository.CustRepository;
import com.team.util.StringUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustService.class);
	
	@Autowired
	CustRepository custRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	/**
	 * 아이디 찾기 service - 김시우
	 */
	public Map<String, String> findId(Map<String, String> info) 
	{
		
		logger.debug("아이디찾기 service");
		logger.debug("info 확인 ::" + info.toString());
		
		// repository에 반환 값 받을 list
		List<CustBas> listRes = new ArrayList<CustBas>();

		// info의 값들을 사용하여 CustBasSaveDTO의 새로운 인스턴스 생성
        CustBasSaveDTO custBasSaveDTO = CustBasSaveDTO.builder()
                .custNm(info.get("custNm"))
                .custBirth(info.get("cust_birth"))
                .custEmail(info.get("cust_email"))
                .build();

        // DTO -> entity
        CustBas custBasEntity = custBasSaveDTO.toEntity();
        logger.debug(StringUtil.toString(custBasEntity));
        
        // repository 전달
        listRes = custRepository.findById(custBasEntity.getCustNm(), custBasEntity.getCustBirth(), custBasEntity.getCustEmail());
        
        Map<String, String> mapReq = new HashMap<>();
        String listEmpYn = "";	// 빈 list인지 체크
        /*
         * list가 null 또는 비어있을때 		: listEmpYn = Y
         * list가 null 또는 비어있지 않을때 	: listEmpYn = N이고, entity -> DTO로 변환
         */
        if(listRes == null || listRes.isEmpty()) 
        {
        	listEmpYn = "Y";
        }
        else 
        {
        	listEmpYn = "N";
        	
        	// entity -> DTO
        	CustBasBringDTO custBasBringDTO = new CustBasBringDTO(listRes.get(0));
        	
        	
        	mapReq.put("custId", custBasBringDTO.getCustId());
        	mapReq.put("custNm", custBasBringDTO.getCustNm());
        }
        
        mapReq.put("listEmpYn", listEmpYn);
        
        
        return mapReq;
	}
	
	
	/**
	 * 비밀번호 찾기 service - 김시우
	 */
	public Map<String, String> findPw(Map<String, String> info) 
	{
		
		logger.debug("비밀번호찾기 service");
		logger.debug("info 확인 ::" + info.toString());		
		
		// repository에 반환 값 받을 list
		List<CustBas> listRes = new ArrayList<CustBas>();

		// info의 값들을 사용하여 CustBasSaveDTO의 새로운 인스턴스 생성
        CustBasSaveDTO custBasSaveDTO = CustBasSaveDTO.builder()
                .custId(info.get("custId"))
                .custBirth(info.get("cust_birth"))
                .custEmail(info.get("cust_email"))
                .build();

        // DTO -> entity
        CustBas custBasEntity = custBasSaveDTO.toEntity();
        logger.debug(StringUtil.toString(custBasEntity));
        
        // repository 전달
        listRes = custRepository.findByPw(custBasEntity.getCustId(), custBasEntity.getCustBirth(), custBasEntity.getCustEmail());
        
        Map<String, String> mapReq = new HashMap<>();
        String listEmpYn = "";	// 빈 list인지 체크
        
        /*
         * list가 null 또는 비어있을때 		: listEmpYn = Y
         * list가 null 또는 비어있지 않을때 	: listEmpYn = N이고, entity -> DTO로 변환
         */
        if(listRes == null || listRes.isEmpty()) 
        {
        	listEmpYn = "Y";
        }
        else 
        {
        	listEmpYn = "N";
        	
        	// entity -> DTO
        	CustBasBringDTO custBasBringDTO = new CustBasBringDTO(listRes.get(0));
        	
        	mapReq.put("cust_email", custBasBringDTO.getCustEmail());
        }
        
        mapReq.put("listEmpYn", listEmpYn);
		
        return mapReq;
	}
	
	/**
	 * 시큐리티 service - 문기연
	 */
	@Override
	public UserDetails loadUserByUsername(String custId) throws UsernameNotFoundException {
		CustBas custBas = custRepository.findById(custId)
        
				.orElseThrow(()-> {  //원하는 객체(id)를 얻지 못하면 예외 처리
					return new UsernameNotFoundException("해당 유저를 찾을 수 없습니다."); //예외처리 메시지
				});
                
		return new PrincipalDetails(custBas);
	}
	
	/**
	 * 회원정보 저장 service - 문기연
	 */
	public Long save(CustBasSaveDTO saveDto) {
		
		return custRepository.save(CustBas.builder()
				.custId(saveDto.getCustId())
				.custPassword(bCryptPasswordEncoder.encode(saveDto.getCustPassword()))
				.build()).getId();
	}


}
