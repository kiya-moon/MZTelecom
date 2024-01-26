package com.team.mztelecom.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.team.mztelecom.repository.CustRepository;

@Service
public class CustChgPwService {

	private static final Logger logger = LoggerFactory.getLogger(CustChgPwService.class);
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	CustRepository custRepository;
	
	/**
	 * 비밀번호 찾기 메일 내용 생성 method - 김시우
	 * 
	 * @param memberEmail
	 * @return
	 */
    public void createMailAndChangePassword(Map<String, String> temp) 
    {
    	
    	logger.debug("temp 확인 ::: " + temp.toString());
    	
        String tempPw = getTempPassword();
        Map<String, String> mailInfo = new HashMap<>();
        
        mailInfo.put("address", temp.get("cust_email"));
        mailInfo.put("title", "MZTelecom 임시비밀번호 안내 이메일 입니다.");
        mailInfo.put("message", "안녕하세요. MZTelecom 임시비밀번호 안내 관련 이메일 입니다." + " 회원님의 임시 비밀번호는 "
                + tempPw + " 입니다." + "로그인 후에 비밀번호를 변경을 해주세요");
        
        updatePassword(tempPw, temp.get("cust_email"), temp.get("custId"));
        
        mailSend(mailInfo);
        
    }
    
    //임시 비밀번호로 업데이트
    public void updatePassword(String tempPassWord, String custEmail, String custId)
    {
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	
    	logger.debug("암호화 전 임시비밀번호 :: " + tempPassWord);
    	// 비밀번호 암호화 하기
    	String encPw = encoder.encode(tempPassWord);
    	logger.debug("암호화 후 임시비밀번호 :: " + encPw);
    	
    	custRepository.updatePw(encPw, custId);
    }
    
    /**
     *  이메일 인증 메일 내용 생성 method - 문기연
     */
	public String createMailCert(String custEmail) {
		Map<String, String> mailInfo = new HashMap<>();
		String certCode = getCertCode();
		
        mailInfo.put("address", custEmail);
        mailInfo.put("title", "MZTelecom 인증 코드 안내 이메일 입니다.");
        mailInfo.put("message", "안녕하세요, MZTelecom 입니다. \n인증코드 6자리를 진행 중인 화면에 입력하고 인증을 완료해주세요." 
        		+ "\n인증코드 : " + certCode);
       
        mailSend(mailInfo);
        
        return certCode;
	}
	
	
    /**
     *  랜덤으로 인증코드 만들기 method - 문기연
     */
	public String getCertCode() {
		Random random = new Random();
		
        // 100000에서 999999 사이의 랜덤 숫자 생성
        int randomcode = 100000 + random.nextInt(900000);

        // 숫자를 문자열로 변환하여 반환
        return String.valueOf(randomcode);
	}
	
    
    /**
     * 랜덤으로 임시비밀번호 만들기 method - 김시우
     * @return
     */
    public String getTempPassword()
    {
    	
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) 
        {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        
        return str;
    }
    
    
    /**
     * 메일 보내기 method - 김시우
     * @param mailDTO
     */
    public void mailSend(Map<String, String> mailInfo) 
    {
    	
    	logger.debug("메일 보내기");
    	logger.debug("mailInfo ::: " + mailInfo.toString());
    	
        // 이메일 발송
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(mailInfo.get("address"));
        message.setSubject(mailInfo.get("title"));
        message.setText(mailInfo.get("message"));
        message.setFrom("syoo91@naver.com");
        message.setReplyTo("syoo91@naver.com");
        
        logger.debug("message 내용 확인 ::: " + message);
        
        mailSender.send(message);
    }

    
    
    
    
}
