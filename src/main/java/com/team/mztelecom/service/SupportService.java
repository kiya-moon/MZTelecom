package com.team.mztelecom.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.FAQ;
import com.team.mztelecom.domain.QnA;
import com.team.mztelecom.dto.FAQDTO;
import com.team.mztelecom.dto.QnADTO;
import com.team.mztelecom.repository.FAQRepository;
import com.team.mztelecom.repository.QnARepository;

@Service
public class SupportService {

	private static final Logger logger = LoggerFactory.getLogger(SupportService.class);
	
	@Autowired
	QnARepository supportRepository;
	
	@Autowired
	FAQRepository fAQRepository;
	
	/**
	 * 문의하기 등록 - 김시우
	 * 
	 * @param inCategory
	 * @param email
	 * @param inContents
	 */
	public void sendQnA(String inCategory, String email, String inContents) {
		
		logger.debug("서비스");
		
		QnA qna = QnA.builder()
				.category(inCategory)
				.email(email)
				.details(inContents)
				.build();
		
		supportRepository.save(qna);
		
	}
	
	/**
	 * 문의내역 조회하기 - 김시우
	 * 관리자 페이지에서 작성된 문의내역 조회
	 * 
	 * @return
	 */
	public List<QnADTO> getQnA() {
		
		List<QnA> qnaList = supportRepository.findAll();
		List<QnADTO> qnaDTOList = new ArrayList<>();
		
		for(QnA qna : qnaList) {
			
			QnADTO qnaDTO = QnADTO.builder()
							.id(qna.getId())
							.category(qna.getCategory())
							.email(qna.getEmail())
							.details(qna.getDetails())
							.build();
			
			qnaDTOList.add(qnaDTO);
		}
		
		return qnaDTOList;
	}
	
	public List<FAQDTO> getFAQ() {
		
		List<FAQ> inFAQList = fAQRepository.findAll();
		List<FAQDTO> outDTOList = new ArrayList<>();
		
		for(FAQ inFAQ : inFAQList) {
			
			FAQDTO inFAQDTO = FAQDTO.builder()
								.id(inFAQ.getId())
								.titleFAQ(inFAQ.getTitleFAQ())
								.detailFAQ(inFAQ.getDetailFAQ())
								.build();

			outDTOList.add(inFAQDTO);
		}
		
		
		return outDTOList;
	}
	
	
}
