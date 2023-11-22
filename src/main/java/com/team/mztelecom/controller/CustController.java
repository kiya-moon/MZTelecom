package com.team.mztelecom.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team.mztelecom.dto.CustBasDTO;
import com.team.mztelecom.dto.CustBasSaveDTO;
import com.team.mztelecom.service.CustService;
import com.team.util.DateUtil;

@Controller
public class CustController {
	
	@Autowired
	CustService custService;
	
	private static final Logger logger = LoggerFactory.getLogger(CustController.class);
	
	// 아이디 찾기
    @PostMapping(value = "/findId")
    @ResponseBody
    public Map<String, String> findId(@RequestParam("custNm") String custNm
									,@RequestParam("year") String year
									,@RequestParam("month") String month
									,@RequestParam("day") String day
									,@RequestParam("id-email-box") String emailId
									,@RequestParam("id-domain-list") String domain ) {
		
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
		
		buff.delete(0, buff.length());
		logger.debug("birth::::" + birth);
		
		// 이메일 합쳐주기
		buff.append(emailId)
			.append("@")
			.append(domain);
		
		email = buff.toString();
		logger.debug("email :::" + email);
		
		
		Map<String, String> findInfo = new HashMap<>();
		
		findInfo.put("custNm", custNm);
		findInfo.put("birth", birth);
		findInfo.put("email", email);
		
		custService.findId(findInfo);
		
		// 스크립트단에 넘어가는지 테스트
        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("birth", birth);
        responseMap.put("email", email);

        return responseMap;
    }
}
