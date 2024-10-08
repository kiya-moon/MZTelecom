package com.team.mztelecom.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team.mztelecom.dto.CustBasDTO;
import com.team.mztelecom.service.CustChgPwService;
import com.team.mztelecom.service.CustService;
import com.team.util.Utiles;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CustController {

	private final CustService custService;
	
	private final CustChgPwService custChgPwService;

	private static final Logger logger = LoggerFactory.getLogger(CustController.class);

	/**
	 * 아이디 찾기 controller - 김시우
	 * 
	 * @param custNm
	 * @param year
	 * @param month
	 * @param day
	 * @param emailId
	 * @param domain
	 * @return
	 */
	@PostMapping(value = "/findId")
	@ResponseBody
	public Map<String, String> findId(@RequestParam("custNm") String custNm, @RequestParam("id-year") String year,
			@RequestParam("id-month") String month, @RequestParam("id-day") String day,
			@RequestParam("id-email-box") String emailId, @RequestParam("id-domain-list") String domain) {

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
		buff.append(year).append(month).append(day);

		birth = buff.toString();
		logger.debug("birth::::" + birth);

		// 버퍼 초기화
		buff.delete(0, buff.length());

		// 이메일 합쳐주기
		buff.append(emailId).append("@").append(domain);

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
	 * 
	 * @param custId
	 * @param year
	 * @param month
	 * @param day
	 * @param emailId
	 * @param domain
	 * @return
	 */
	@PostMapping(value = "/findPw")
	@ResponseBody
	public Map<String, String> findPw(@RequestParam("custId") String custId, @RequestParam("pw-year") String year,
			@RequestParam("pw-month") String month, @RequestParam("pw-day") String day,
			@RequestParam("pw-email-box") String emailId, @RequestParam("pw-domain-list") String domain) {

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
		buff.append(year).append(month).append(day);
		birth = buff.toString();

		buff.delete(0, buff.length());
		logger.debug("birth 확인:::" + birth);

		// 버퍼 초기화
		buff.delete(0, buff.length());

		// 이메일 합쳐주기
		buff.append(emailId).append("@").append(domain);

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
	 * 
	 * @param custId
	 * @param custNm
	 * @param custPassword
	 * @param custPasswordCheck
	 * @param custIdfyNo
	 * @param custNo
	 * @param custEmail
	 * @param emailDomain
	 * @param redirectAttributes
	 * @return
	 */
	@PostMapping(value = "/signup")
	public String signup(@RequestParam("custId") String custId, @RequestParam("custNm") String custNm,
			@RequestParam("custPassword") String custPassword,
			@RequestParam("custPasswordCheck") String custPasswordCheck, @RequestParam("custIdfyNo") String custIdfyNo,
			@RequestParam("custNo") String custNo, @RequestParam("custEmail") String custEmail,
			@RequestParam("emailDomain") String emailDomain,
			RedirectAttributes redirectAttributes) {

		logger.debug("컨트롤러 도착 확인");
		
		String idfyChk =custService.IdfyNoVrfct(custIdfyNo); // 입력받은 주민번호랑 기존 회원의 주민번호 비교 service - 김시우
		
		CustBasDTO request = new CustBasDTO();
		
		// 기존 회원 중에 일치하는 주민번호가 없을 시 회원가입 진행 - 김시우
		if(idfyChk.equals("Y")) 
		{
			request.setCustId(custId);
			request.setCustNm(custNm);
			request.setCustPassword(custPassword);
			request.setCustPasswordCheck(custPasswordCheck);
			request.setCustIdfyNo(custIdfyNo);
			request.setCustNo(custNo);
			request.setCustEmail(custEmail);
			request.setEmailDomain(emailDomain);
			
			custService.save(request);
		}
		// 기존 회원과 일치하는 주민번호를 입력 했을 시
		else
		{
	        redirectAttributes.addFlashAttribute("errorMessage", "주민번호를 다시 확인해주세요."); // 리다이렉트 시 에러 메시지 전달
	        
	        return "redirect:/signup"; // 다시 회원가입 페이지로 리다이렉트
		}

		return "redirect:/login";
	}
	
	/**
	 * 회원가입 이메일 중복 확인 - 문기연
	 * 
	 * @param custEmail
	 * @return
	 */
	@GetMapping(value = "/checkEmailDuplicate")
	public ResponseEntity<String> checkEmailDuplicate(@RequestParam("custEmail") String custEmail) {
		logger.debug("이메일 중복확인 도착 :: custEmail - " + custEmail);
		
		boolean isEmailDuplicate = custService.isEmailDuplicate(custEmail);
		
		// 중복여부 리턴
		if (isEmailDuplicate) {
			return ResponseEntity.badRequest().body("사용 중인 이메일 입니다.");
		} else {
			return ResponseEntity.ok("사용 가능한 이메일");
		}
	}

	/**
	 * 회원가입 이메일 인증 - 문기연
	 * 
	 * @param custEmail
	 * @return
	 */
	@GetMapping(value = "/sendEmailCert")
	public ResponseEntity<String> sendEmailCert(@RequestParam("custEmail") String custEmail) {
		String certCode;
		logger.debug("이메일 인증 도착 :: custEmail - " + custEmail);
		
		try {
			// 이메일 전송
			if (!Utiles.isNullOrEmpty(custEmail)) {

				logger.debug("이메일 발송 시작");

				certCode = custChgPwService.createMailCert(custEmail);
				
				return ResponseEntity.ok(certCode);

			}
		} catch (Exception e) {
			logger.error("이메일 발송 실패", e);
			return ResponseEntity.badRequest().body("메일 발송 중 에러가 발생했습니다.");
		}
		return null;
	}
	
	/**
	 * ID 중복확인 - 박지윤
	 * 
	 * @param custId
	 * @return
	 */
	@GetMapping(value = "/checkDuplicate")
	public ResponseEntity<String> checkDuplicate(@RequestParam("custId") String custId) {
		boolean isDuplicate = custService.isIdDuplicate(custId);
		
		// 입력한 아이디가 있는지 확인
		if (isDuplicate) {
			return ResponseEntity.badRequest().body("사용 중인 아이디 입니다.");
		} else {
			return ResponseEntity.ok("사용 가능한 아이디입니다.");
		}
	}
}
