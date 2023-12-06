package com.team.mztelecom.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team.mztelecom.dto.CustBasSaveDTO;
import com.team.mztelecom.service.CustService;

import jakarta.validation.Valid;

@Controller
public class CustController {
	
	@Autowired
	CustService custService;
	
	private static final Logger logger = LoggerFactory.getLogger(CustController.class);
	
	/*
	 * 아이디 찾기 controller - 김시우
	 */
    @PostMapping(value = "/findId.do")
    @ResponseBody
    public Map<String, String> findId(@RequestParam("custNm") String custNm
									,@RequestParam("id-year") String year
									,@RequestParam("id-month") String month
									,@RequestParam("id-day") String day
									,@RequestParam("id-email-box") String emailId
									,@RequestParam("id-domain-list") String domain)
    {
		
		logger.debug("아이디 찾기 들어옴");
		
		StringBuffer buff = new StringBuffer();
	
		// 생년월일 담아 줄 변수
		String birth = "";
		// 이메일을 담아 줄 변수
		String email = "";

		// 두자리 월, 일로 변경
		month = String.format("%02d", Integer.parseInt(month));
		day = String.format("%02d", Integer.parseInt(day));
		
		// 생년월일 합쳐주기
		buff.append(year)
			.append(month)
			.append(day);
		
		birth = buff.toString();
		logger.debug("birth::::" + birth);
		
		// 버퍼 초기화
		buff.delete(0, buff.length());
		
		// 이메일 합쳐주기
		buff.append(emailId)
			.append("@")
			.append(domain);
		
		email = buff.toString();
		logger.debug("email :::" + email);
		
		
		Map<String, String> findIdReq = new HashMap<>();
		Map<String, String> findIdRes = new HashMap<>();
		
		findIdReq.put("custNm", custNm);
		findIdReq.put("cust_birth", birth);
		findIdReq.put("cust_email", email);
		
		findIdRes = custService.findId(findIdReq);
		
		logger.debug("리턴값 확인 ::: " + findIdRes);

        return findIdRes;
    }
    
    /**
     * 비밀번호 찾기 controller - 김시우
     */
    @PostMapping(value = "/findPw.do")
    @ResponseBody
    public Map<String, String> findPw(@RequestParam("custId") String custId
									,@RequestParam("pw-year") String year
									,@RequestParam("pw-month") String month
									,@RequestParam("pw-day") String day
									,@RequestParam("pw-email-box") String emailId
									,@RequestParam("pw-domain-list") String domain) 
    {
    	
    	logger.debug("pw controller 진입");
    	
    	
    	StringBuffer buff = new StringBuffer();
    	
		// 생년월일 담아 줄 변수
		String birth = "";
		// 이메일을 담아 줄 변수
		String email = "";

		// 두자리 월, 일로 변경
		month = String.format("%02d", Integer.parseInt(month));
		day = String.format("%02d", Integer.parseInt(day));
		
		// 생년월일 합쳐주기
		buff.append(year)
			.append(month)
			.append(day);
		
		birth = buff.toString();
		
		buff.delete(0, buff.length());
		logger.debug("birth 확인:::" + birth);
		
		// 버퍼 초기화
		buff.delete(0, buff.length());
				
		// 이메일 합쳐주기
		buff.append(emailId)
			.append("@")
			.append(domain);
			
		email = buff.toString();
		logger.debug("email 확인:::" + email);
		
		Map<String, String> findPwReq = new HashMap<>();
		Map<String, String> findPwRes = new HashMap<>();
    	
		// 클라이언트에서 받은 값 세팅
		findPwReq.put("custId", custId);
		findPwReq.put("cust_birth", birth);
		findPwReq.put("cust_email", email);
		
		// 서비스
		findPwRes = custService.findPw(findPwReq);
		
		logger.debug("리턴값 확인 ::: " + findPwRes);
    	
    	return findPwRes;
    }

    
    /**
     * 회원가입 Controller - 문기연
     */
    @PostMapping(value= "/signup")
    @ResponseBody
	public String signup(@RequestParam("custId") String custId
			, @RequestParam("custNm") String custNm
			, @RequestParam("custPassword") String custPassword
			, @RequestParam("custPasswordCheck") String custPasswordCheck
			, @RequestParam("custIdfyNo") String custIdfyNo
			, @RequestParam("custNo") String custNo
			, @RequestParam("custEmail") String cusstEmail) {
		
    	logger.debug("컨트롤러 도착 확인");
    	logger.debug(custIdfyNo);
    	
    	CustBasSaveDTO request = new CustBasSaveDTO();
    	request.setCustId(custId);
    	request.setCustNm(custNm);
    	request.setCustPassword(custPassword);
    	request.setCustPasswordCheck(custPasswordCheck);
    	request.setCustIdfyNo(custIdfyNo);
    	request.setCustBirth(custIdfyNo.substring(0,6));
    	request.setCustNo(custNo);
    	
    	String sex = custIdfyNo.substring(7, 8);
    	if (sex.equals("1") || sex.equals("3")) {
    	    request.setCustSex("남성");
    	} else if (sex.equals("2") || sex.equals("4")) {
    		request.setCustSex("여성");
    	}
    	
    	request.setCustEmail(cusstEmail);
    	
		custService.save(request);    		
		
		return "redirect:/content/login";
	}
}
