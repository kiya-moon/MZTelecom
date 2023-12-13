package com.team.mztelecom.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.PurRevBoard;
import com.team.mztelecom.dto.CustBasDTO;
import com.team.mztelecom.dto.PurRevBoardDTO;
import com.team.mztelecom.repository.PurRevBoardRepository;
import com.team.mztelecom.service.PurRevBoardService;
import com.team.util.StringUtil;
import com.team.util.Utiles;

import jakarta.transaction.Transactional;

@Service
public class PurRevBoardService{
	
	private static final Logger logger = LoggerFactory.getLogger(PurRevBoardService.class);
	
	@Autowired
	PurRevBoardRepository purRevBoardRepository;
	
	@Autowired
	CustService custService;

	/**
	 * 검색 안했을 시 목록 보여주는 서비스 - 김시우
	 */
    public Page<PurRevBoardDTO> PurRevBoardList(Pageable pageable) {
		
		logger.debug("PurRevBoardList 진입");
		
        Page<PurRevBoard> purRevBoards = purRevBoardRepository.findAll(pageable);
        
        logger.debug("purRevBoardRepository 나옴");
        
        logger.debug("return 전");
        return getPurRevBoardDTO(pageable, purRevBoards);
    }
	
	/**
	 * 검색 시 목록 보여주는 서비스 - 김시우
	 */
	public Page<PurRevBoardDTO> searchingPurRevBoard(String keyword, Pageable pageable, String catgo) {
	    logger.debug("하나로 통일한 서비스");

	    Page<PurRevBoard> purRevBoards;

	    if ("title".equals(catgo)) {
	    	// 글제목
	        purRevBoards = purRevBoardRepository.findByBoardTitleContains(keyword, pageable);
	    } else {
	    	// 작성자
	        purRevBoards = purRevBoardRepository.findByWriterContains(keyword, pageable);
	    }

	    logger.debug("getTotalPages ::: " + purRevBoards.getTotalPages());
	    logger.debug("getTotalElements ::: " + purRevBoards.getTotalElements());
	    logger.debug("getNumber ::: " + purRevBoards.getNumber());
	    logger.debug("getSize ::: " + purRevBoards.getSize());
	    logger.debug("getContent ::: " + purRevBoards.getContent());

	    return getPurRevBoardDTO(pageable, purRevBoards);
	}
	
	/**
	 * 구매후기 작성시 저장처리
	 * 
	 * @param inPurRevBoardDTO
	 */
	@Transactional
	public void writePurRev(PurRevBoardDTO inPurRevBoardDTO) {
		logger.debug("확인");
		
		// 작성일자
	    Date nowDate = new Date();
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
	    String date = simpleDateFormat.format(nowDate);
		
		PurRevBoard	inpurRevBoard;
		
		// 작성자 정보 가지고 오기
		List<CustBas> inCustBas = new ArrayList<>();
		inCustBas = custService.getId(inPurRevBoardDTO.getWriter());
		
		logger.debug("getIntmNm ::::" + inPurRevBoardDTO.getIntmNm());
		
		// 첨부파일 있을 경우
		if(!Utiles.isNullOrEmpty(inPurRevBoardDTO.getAttachmentId())) {
			logger.debug("첨부파일 있다.");
			inpurRevBoard = PurRevBoard.builder()
					.intmNm(inPurRevBoardDTO.getIntmNm())
					.boardTitle(inPurRevBoardDTO.getBoardTitle())
					.boardDate(date)
					.boardDetail(inPurRevBoardDTO.getBoardDetail())
					.attachmentId(inPurRevBoardDTO.getAttachmentId())
					.writer(inPurRevBoardDTO.getWriter())
					.custBas(inCustBas.get(0))
					.build();
			
		}
		// 첨부파일 없을 경우
		else
		{
			logger.debug("첨부파일 없다");
			inpurRevBoard = PurRevBoard.builder()
					.intmNm(inPurRevBoardDTO.getIntmNm())
					.boardTitle(inPurRevBoardDTO.getBoardTitle())
					.boardDate(date)
					.boardDetail(inPurRevBoardDTO.getBoardDetail())
					.writer(inPurRevBoardDTO.getWriter())
					.custBas(inCustBas.get(0))
					.build();
		}
		
		// 구매후기 저장
		purRevBoardRepository.save(inpurRevBoard);
	}
	

	/**
	 * 구매후기 상세페이지
	 * 
	 * @param id
	 * @return
	 */
	public PurRevBoardDTO purRevDetail(Long id) {
		
		Optional<PurRevBoard> outPurRevBoard = purRevBoardRepository.findById(id);
		PurRevBoardDTO inPurRevBoardDto = PurRevBoardDTO.builder()
				.writer(outPurRevBoard.get().getCustBas().getCustId())
				.intmNm(outPurRevBoard.get().getIntmNm())
				.boardTitle(outPurRevBoard.get().getBoardTitle())
				.boardDetail(outPurRevBoard.get().getBoardDetail())
				.attachmentId(outPurRevBoard.get().getAttachmentId())
				.build();
		
		
		return inPurRevBoardDto;
	}
	
	
	
	
	/**
	 * 공통소스 메서드
	 * 
	 */
    private Page<PurRevBoardDTO> getPurRevBoardDTO(Pageable pageable, Page<PurRevBoard> purRevBoards) {
        List<PurRevBoardDTO> purRevBoardDTOs = new ArrayList<>();
        
        for (PurRevBoard purRevBoard : purRevBoards) {
        	
        	PurRevBoardDTO result = PurRevBoardDTO.builder()
        			.id(purRevBoard.getId())
        			.intmNm(purRevBoard.getIntmNm())
        			.boardTitle(purRevBoard.getBoardTitle())
        			.boardDate(purRevBoard.getBoardDate())
        			.boardDetail(purRevBoard.getBoardDetail())
        			.attachmentId(purRevBoard.getAttachmentId())
        			.writer(purRevBoard.getCustBas().getCustId())
                    .build();
        	purRevBoardDTOs.add(result);
        }

        return new PageImpl<>(purRevBoardDTOs, pageable, purRevBoards.getTotalElements());
    }
    
    
    
	
    
    
}
