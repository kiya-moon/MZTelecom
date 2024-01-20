package com.team.mztelecom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.QnA;
import com.team.mztelecom.repository.SupportRepository;

@Service
public class SupportService {

	private static final Logger logger = LoggerFactory.getLogger(SupportService.class);
	
	@Autowired
	SupportRepository supportRepository;
	
	public void sendQnA(String inCategory, String email, String inContents) {
		
		logger.debug("서비스");
		
		QnA qna = QnA.builder()
				.category(inCategory)
				.email(email)
				.details(inContents)
				.build();
		
		supportRepository.save(qna);
		
	}
	
	
	
}
