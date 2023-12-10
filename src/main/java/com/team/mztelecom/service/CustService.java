package com.team.mztelecom.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.dto.CustBasBringDTO;
import com.team.mztelecom.dto.CustBasSaveDTO;
import com.team.mztelecom.repository.CustRepository;
import com.team.util.StringUtil;
import com.team.util.Utiles;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustService {	

	private static final Logger logger = LoggerFactory.getLogger(CustService.class);
	
	private final CustRepository custRepository;
	
	private final CustChgPwService custChgPwService;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
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
        logger.debug("확인"+StringUtil.toString(custBasSaveDTO));

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
        	
        	mapReq.put("cust_email", custBasBringDTO.getCustEmail());	// 이메일 전송 및 화면에서 사용하기 위해
        	mapReq.put("custId", custBasBringDTO.getCustId());
        }
        
        
        // 이메일 전송
        if(!Utiles.isNullOrEmpty(listEmpYn) && listEmpYn == "N") {
        	
        	logger.debug("이메일 발송 시작");
        	
        	custChgPwService.createMailAndChangePassword(mapReq);
        	
        }
        
        mapReq.put("listEmpYn", listEmpYn);
		
        return mapReq;
	}
	
	/**
	 * 회원정보 저장 service - 문기연
	 */
	public CustBas save(CustBasSaveDTO request) {
		logger.debug("서비스 도착 확인");
		
    	String[] custIdfyNoArr = request.getCustIdfyNo().split(",");
    	request.setCustIdfyNo(custIdfyNoArr[0] + '-' + custIdfyNoArr[1] );
    	
    	request.setCustBirth(request.getCustIdfyNo().substring(0,6));
    	
    	String[] custNoArr = request.getCustNo().split(",");
    	request.setCustNo(custNoArr[0] + '-' + custNoArr[1] + '-' + custNoArr[2]);
    	
    	String sex = request.getCustIdfyNo().substring(7, 8);
    	if (sex.equals("1") || sex.equals("3")) {
    		request.setCustSex("남성");
    	} else if (sex.equals("2") || sex.equals("4")) {
    		request.setCustSex("여성");
    	}
    	
    	String email = request.getCustEmail() + '@'+ request.getEmailDomain();
    	request.setCustEmail(email);
		
		CustBas custBas = CustBas.builder()
                .custId(request.getCustId())
                .custPassword(bCryptPasswordEncoder.encode(request.getCustPassword()))
                .custNm(request.getCustNm())
                .custIdfyNo(request.getCustIdfyNo())
                .custBirth(request.getCustBirth())
                .custEmail(request.getCustEmail())
                .custNo(request.getCustNo())
                .custSex(request.getCustSex())
                .build();

        return custRepository.save(custBas);
	}
	
	// id 중복확인
	public boolean isIdDuplicate(String custId) {
        Optional<CustBas> existingCustomer = custRepository.findByCustId(custId);
        return existingCustomer.isPresent();
    }
	
	
	/* 로그인 - 박지윤 */
	
	@Transactional
	public CustBas whenSocialLogin(String providerTypeCode, String custId, String custNm, String custEmail) {
		Optional<CustBas> opCustBas = findByCustId(custId);
		
		if (opCustBas.isPresent()) { return opCustBas.get(); }
			
		// 소셜 로그인를 통한 가입시 비번 X
		CustBasSaveDTO request = new CustBasSaveDTO();
		request.setCustId(custId);
	    request.setCustPassword("");
	    request.setCustNm(custNm);
	    request.setCustEmail(custEmail);
	    request.setCustNo("Unknown");
	    
	    String uniqueIdfyNo = UUID.randomUUID().toString();
	    request.setCustIdfyNo(uniqueIdfyNo);
	        
	    return save(request);
	}
	
	public Optional<CustBas> findByCustId(String custId) {
		return custRepository.findByCustId(custId);
	}
}
