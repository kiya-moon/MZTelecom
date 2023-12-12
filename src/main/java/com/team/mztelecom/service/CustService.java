package com.team.mztelecom.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.dto.CustBasDTO;
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

		// info의 값들을 사용하여 CustBasSaveDTO의 새로운 인스턴스 생성
        CustBasDTO custBasDTO = CustBasDTO.builder()
                .custNm(info.get("custNm"))
                .custBirth(info.get("cust_birth"))
                .custEmail(info.get("cust_email"))
                .build();

        // DTO -> entity
        CustBas custBasEntity = custBasDTO.toEntity();
        logger.debug(StringUtil.toString(custBasEntity));
        
        List<CustBas> listRes = new ArrayList<CustBas>();
        // repository 전달
        listRes = custRepository.findByDynamicQuery(custBasEntity.getCustId(), custBasEntity.getCustNm(), custBasEntity.getCustBirth(), custBasEntity.getCustEmail());
        
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
        	CustBasDTO outCustBas = new CustBasDTO();
        	
        	for(int i =0; i < listRes.size(); i++) {
        		outCustBas.setCustId(listRes.get(i).getCustId());
        		outCustBas.setCustNm(listRes.get(i).getCustNm());
        	}
        	
        	mapReq.put("custId", outCustBas.getCustId());
        	mapReq.put("custNm", outCustBas.getCustNm());
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
        CustBasDTO custBasDTO = CustBasDTO.builder()
                .custId(info.get("custId"))
                .custBirth(info.get("cust_birth"))
                .custEmail(info.get("cust_email"))
                .build();
        logger.debug("확인"+StringUtil.toString(custBasDTO));

        // DTO -> entity
        CustBas custBasEntity = custBasDTO.toEntity();
        logger.debug(StringUtil.toString(custBasEntity));
        
        // repository 전달
        listRes = custRepository.findByDynamicQuery(custBasEntity.getCustId(), custBasEntity.getCustNm(), custBasEntity.getCustBirth(), custBasEntity.getCustEmail());
        
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
        	CustBasDTO outCustBas = new CustBasDTO();
        	
        	for(int i =0; i < listRes.size(); i++) {
        		outCustBas.setCustEmail(listRes.get(i).getCustEmail());
        		outCustBas.setCustId(listRes.get(i).getCustId());
        	}
        	
        	mapReq.put("cust_email", outCustBas.getCustEmail());	// 이메일 전송 및 화면에서 사용하기 위해
        	mapReq.put("custId", outCustBas.getCustId());
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
	public CustBas save(CustBasDTO request) {
		logger.debug("서비스 도착 확인");
		
    	String[] custIdfyNoArr = request.getCustIdfyNo().split(",");
    	request.setCustIdfyNo(custIdfyNoArr[0] + "-" + custIdfyNoArr[1] );
    	
    	request.setCustBirth(request.getCustIdfyNo().substring(0,6));
    	
    	String[] custNoArr = request.getCustNo().split(",");
    	request.setCustNo(custNoArr[0] + "-" + custNoArr[1] + "-" + custNoArr[2]);
    	
    	String sex = request.getCustIdfyNo().substring(7, 8);
    	if (sex.equals("1") || sex.equals("3")) {
    		request.setCustSex("남성");
    	} else if (sex.equals("2") || sex.equals("4")) {
    		request.setCustSex("여성");
    	}
    	
    	String email = request.getCustEmail() + '@'+ request.getEmailDomain();
    	request.setCustEmail(email);
		
		CustBasDTO custBasDTO = CustBasDTO.builder()
                .custId(request.getCustId())
                .custPassword(bCryptPasswordEncoder.encode(request.getCustPassword()))
                .custNm(request.getCustNm())
                .custIdfyNo(request.getCustIdfyNo())
                .custBirth(request.getCustBirth())
                .custEmail(request.getCustEmail())
                .custNo(request.getCustNo())
                .custSex(request.getCustSex())
                .build();
		
		CustBas custBas = custBasDTO.toEntity();

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
		CustBasDTO request = new CustBasDTO();
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
	
	
	/**
	 * 구매후기 구매시 회원의 Long id 조회 - 김시우
	 * 
	 */
	public List<CustBas> getId(String inCustId) {
		
		// repository에 반환 값 받을 list
		List<CustBas> outCustList = new ArrayList<CustBas>();

		// info의 값들을 사용하여 CustBasSaveDTO의 새로운 인스턴스 생성
		CustBasDTO custBasDTO = CustBasDTO.builder()
		        .custId(inCustId)
		        .build();
		logger.debug("확인"+StringUtil.toString(custBasDTO));

		// DTO -> entity
		CustBas custBasEntity = custBasDTO.toEntity();
		logger.debug(StringUtil.toString(custBasEntity));
		
		outCustList = custRepository.findByDynamicQuery(custBasEntity.getCustId(), custBasEntity.getCustNm(), custBasEntity.getCustBirth(), custBasEntity.getCustEmail());
		logger.debug("outCustList :::" + StringUtil.toString(outCustList));
		
		return outCustList;
	}
}
