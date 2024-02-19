package com.team.mztelecom.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final CustChgPwService custChgPwService;
	
	private final CustRepository custRepository;
	
	// 로그인 확인
	public boolean isLoggedIn() {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    return auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal());
	}
	
	/**
	 * 회원 조회를 위한 기본 세팅 - 김시우
	 * 
	 * @param inCustBasDTO
	 * @return
	 */
	public CustBasDTO getMember(CustBasDTO inCustBasDTO) {
		
		CustBas MemberList = CustBas.builder()
				.custId(inCustBasDTO.getCustId())
				.custNm(inCustBasDTO.getCustNm())
				.custBirth(inCustBasDTO.getCustBirth())
				.custEmail(inCustBasDTO.getCustEmail())
				.build();
		
		List<CustBas> outMemberList = custRepository.findByDynamicQuery(MemberList.getCustId(), MemberList.getCustNm(), MemberList.getCustBirth(), MemberList.getCustEmail());
		
		// 소셜 회원가입시 핸드폰 번호가 처음에 unknown으로 DB에 저장되어 null 값으로 처리
		String custNo = null;
		if(outMemberList.get(0).getCustNo().equals("Unknown")) 
		{
			custNo = null;
		}
		else
		{
			custNo = outMemberList.get(0).getCustNo();
		}
		
		CustBasDTO inDTO = CustBasDTO.builder()
				.id(outMemberList.get(0).getId())
				.custId(outMemberList.get(0).getCustId())
				.custNm(outMemberList.get(0).getCustNm())
				.custPassword(outMemberList.get(0).getCustPassword())
				.custBirth(outMemberList.get(0).getCustBirth())
				.custNo(custNo)
				.custSex(outMemberList.get(0).getCustSex())
				.custAddress(outMemberList.get(0).getCustAddress())
				.custEmail(outMemberList.get(0).getCustEmail())
				.build();
		
		logger.debug("inDTO ::: " + StringUtil.toString(inDTO));
		
		return inDTO; 
				
	}
	
	/**
	 * 구매후기 작성자에 넣을 이름 - 김시우
	 * 
	 * @param inWriter
	 * @return
	 */
	public String findName(CustBasDTO inDTO){
		
		CustBas inCustBas = inDTO.toEntity();
		
		logger.debug("inCustBas :: " + StringUtil.toString(inCustBas));
		
		List<CustBas> outList = new ArrayList<CustBas>();
		outList = custRepository.findByDynamicQuery(inCustBas.getCustId(), inCustBas.getCustNm(), inCustBas.getCustBirth(), inCustBas.getCustEmail());
		
		String custNm = outList.get(0).getCustNm();
		
		return custNm;
	}
	
	/**
	 * 아이디 찾기 service - 김시우
	 * 
	 * @param info
	 * @return
	 */
	public Map<String, String> findId(Map<String, String> info) 
	{
		
		logger.debug("아이디찾기 service");
		logger.debug("info 확인 ::" + info.toString());
		
		// info의 값들을 사용하여 CustBasSaveDTO의 새로운 인스턴스 생성
        CustBasDTO custBasDTO = CustBasDTO.builder()
                .custNm(info.get("custNm"))
                .custBirth(info.get("cust_birth"))
                .custEmail(info.get("cust_email"))
                .build();

        // DTO -> entity
        CustBas custBasEntity = custBasDTO.toEntity();
        
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
        	CustBasDTO outDTO = new CustBasDTO();
        	
        	for(int i =0; i < listRes.size(); i++) {
        		
        		outDTO = CustBasDTO.builder()
        				.custId(listRes.get(i).getCustId())
						.custNm(listRes.get(i).getCustNm())
						.build();
        		
        	}
        	
        	mapReq.put("custId", outDTO.getCustId());
        	mapReq.put("custNm", outDTO.getCustNm());
        }
        
        mapReq.put("listEmpYn", listEmpYn);
        
        
        return mapReq;
	}
	
	/**
	 * 비밀번호 찾기 service - 김시우
	 * 
	 * @param info
	 * @return
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
        	CustBasDTO outDTO = new CustBasDTO();
        	
        	for(int i =0; i < listRes.size(); i++) {
        		
        		outDTO = CustBasDTO.builder()
        						.custEmail(listRes.get(i).getCustEmail())
        						.custId(listRes.get(i).getCustId())
        						.build();
        	}
        	
        	mapReq.put("cust_email", outDTO.getCustEmail());	// 이메일 전송 및 화면에서 사용하기 위해
        	mapReq.put("custId", outDTO.getCustId());
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
	 * @param request
	 * @return
	 */
	public CustBas save(CustBasDTO request) {
		logger.debug("서비스 도착 확인");
		
		// 회원가입 페이지에서 custIdfyNo가 '123456, 1234567' 형태로 들어오므로 split 처리하여 배열 처리 
    	String[] custIdfyNoArr = request.getCustIdfyNo().split(",");
    	
    	// 소셜 가입을 위한 조건문
    	if (custIdfyNoArr.length == 2) {
			// 주민번호 '-' 삽입
		    request.setCustIdfyNo(custIdfyNoArr[0] + "-" + custIdfyNoArr[1]);
		    
		    // 성별 추출
		    String sex = request.getCustIdfyNo().substring(7, 8);
		    if (sex.equals("1") || sex.equals("3")) {
		    	request.setCustSex("남성");
		    } else if (sex.equals("2") || sex.equals("4")) {
		    	request.setCustSex("여성");
		    }
	
		    // 생년월일 추출
		    String birthDay = request.getCustIdfyNo().substring(0,6);
		    if (sex.equals("1") || sex.equals("2")) {
		    	birthDay = "19" + birthDay;
		    	logger.debug("19xx : " + birthDay);
		    } else if (sex.equals("3") || sex.equals("4")) {
		    	birthDay = "20" + birthDay;
		    	logger.debug("20xx : " + birthDay);
		    }
		    request.setCustBirth(birthDay);
	    	    
	    	
		    // 핸드폰번호 '-' 삽입
		    String[] custNoArr = request.getCustNo().split(",");
		    request.setCustNo(custNoArr[0] + "-" + custNoArr[1] + "-" + custNoArr[2]);
		    
		    // 이메일 '@' 삽입
		    String email = request.getCustEmail() + '@'+ request.getEmailDomain();
		    request.setCustEmail(email);
    	}
    	
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
	
	/**
	 * ID 중복확인 - 박지윤
	 * 
	 * @param custId
	 * @return
	 */
	public boolean isIdDuplicate(String custId) {
		// 고객 ID로 이미 존재하는 고객 정보 찾기
        Optional<CustBas> existingCustomer = custRepository.findByCustId(custId);
        
        // 고객 정보가 존재하면 true를 반환 아니라면 false
        return existingCustomer.isPresent();
    }
	
	/**
	 * 소셜 로그인 - 박지윤
	 * 
	 * @param providerTypeCode
	 * @param custId
	 * @param custNm
	 * @param custEmail
	 * @return
	 */
	@Transactional
	public CustBas whenSocialLogin(String providerTypeCode, String custId, String custNm, String custEmail) {
		Optional<CustBas> opCustBas = findByCustId(custId);
		
		// 존재하는 고객인 경우, 해당 고객 정보 반환
		if (opCustBas.isPresent()) { return opCustBas.get(); }
		
		// 새로운 고객 등록을 위해 고객 정보 설정
		CustBasDTO request = new CustBasDTO();
		request.setCustId(custId);
		// 소셜 로그인를 통한 가입시 비번 X
	    request.setCustPassword("");
	    request.setCustNm(custNm);
	    request.setCustEmail(custEmail);
	    request.setCustNo("Unknown");
	    
	    String uniqueIdfyNo = UUID.randomUUID().toString();
	    request.setCustIdfyNo(uniqueIdfyNo);
	       
	    return save(request);
	}
	
	/**
	 * 로그인 - 박지윤
	 * 
	 * @param custId
	 * @return
	 */
	public Optional<CustBas> findByCustId(String custId) {
		return custRepository.findByCustId(custId);
	}
	
	
	/**
	 * 구매후기 작성시 회원 이름으로 회원의 Long id 조회 - 김시우
	 * 
	 * @param inCustNm
	 * @return
	 */
	public List<CustBas> getId(List<CustBasDTO> inCustList) {
		
		// repository에 반환 값 받을 list
		List<CustBas> outCustList = new ArrayList<CustBas>();

		// info의 값들을 사용하여 CustBasSaveDTO의 새로운 인스턴스 생성
		CustBasDTO custBasDTO = CustBasDTO.builder()
				.custId(inCustList.get(0).getCustId())
		        .custNm(inCustList.get(0).getCustNm())
		        .build();

		// DTO -> entity
		CustBas custBasEntity = custBasDTO.toEntity();
		logger.debug(StringUtil.toString(custBasEntity));
		
		outCustList = custRepository.findByDynamicQuery(custBasEntity.getCustId(), custBasEntity.getCustNm(), custBasEntity.getCustBirth(), custBasEntity.getCustEmail());
		logger.debug("outCustList :::" + StringUtil.toString(outCustList));
		
		return outCustList;
	}

	/**
	 * 이메일 중복 확인 - 문기연
	 * @param custEmail
	 * @return
	 */
	public boolean isEmailDuplicate(String custEmail) {
        Optional<CustBas> existingCustEmail = custRepository.findByCustEmail(custEmail);
        logger.debug("이메일 죽복확인 서비스 도착. custEmail :: " + existingCustEmail);
        return existingCustEmail.isPresent();
	}
	
	
	/**
	 * 기존 회원의 주민번호와 회원가입하는 회원의 주민번호가 일치하는 것이 있는지 확인 - 김시우
	 * 
	 * @param inCustIdFyNo
	 * @return
	 */
	public String IdfyNoVrfct(String inCustIdFyNo) {
		
		List<CustBas> outList = custRepository.findAll();
		String idfyChk = "Y";	// 기존 회원과 일치하는 주민번호가 있는지 체크 ==>  N: 일치하는 주민번호 o / Y: 일치하는 주민번호 x
		
    	String[] custIdfyNoArr = inCustIdFyNo.split(",");
		
    	// 입력받은 주민번호 배열의 길이가 1이상 일 때 
    	if (custIdfyNoArr.length > 1) 
    	{
    		// 주민번호 '-' 삽입
    		String custIdFyNo = custIdfyNoArr[0] + "-" + custIdfyNoArr[1];
    		
    		for(int i = 0 ; i < outList.size(); i ++) 
    		{
    			// 기존 고객 중에 같은 주민번호가 존재 할 때
    			if(outList.get(i).getCustIdfyNo().equals(custIdFyNo)) 
    			{
    				idfyChk = "N";
    			}
    		}
		
    	}
		return idfyChk;
	}
}
