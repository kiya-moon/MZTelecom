package com.team.mztelecom.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.PurRevBoard;
import com.team.mztelecom.dto.PurRevBoardDTO;
import com.team.mztelecom.repository.PurRevBoardRepository;
import com.team.mztelecom.service.PurRevBoardService;
import com.team.util.StringUtil;

@Service
public class PurRevBoardServiceImpl implements PurRevBoardService{
	
	private static final Logger logger = LoggerFactory.getLogger(PurRevBoardServiceImpl.class);
	
	@Autowired
	PurRevBoardRepository purRevBoardRepository;

	/**
	 * 검색 안했을 시 목록 보여주는 서비스 - 김시우
	 */
	@Override
    public Page<PurRevBoardDTO> PurRevBoardList(Pageable pageable) {
		
		logger.debug("PurRevBoardList 진입");
		
        Page<PurRevBoard> purRevBoards = purRevBoardRepository.findAll(pageable);
        
        logger.debug("purRevBoardRepository 나옴");
        
        List<PurRevBoardDTO> purRevBoardDTOs = new ArrayList<>();

        for (PurRevBoard purRevBoard : purRevBoards) {
        	PurRevBoardDTO result = PurRevBoardDTO.builder()
                    .purRevBoard(purRevBoard)
                    .build();
        	purRevBoardDTOs.add(result);
        }
        
        logger.debug(StringUtil.toString(purRevBoardDTOs));

        logger.debug("return 전");
        return new PageImpl<>(purRevBoardDTOs, pageable, purRevBoards.getTotalElements());
    }
	
	/**
	 * 검색 시 목록 보여주는 서비스 - 김시우
	 */
	@Override
	public Page<PurRevBoardDTO> searchingPurRevBoard(String keyword, Pageable pageable, String catgo) {
	    logger.debug("하나로 통일한 서비스");

	    Page<PurRevBoard> purRevBoards;

	    if ("title".equals(catgo)) {
	    	logger.debug("확인1");
	        purRevBoards = purRevBoardRepository.findByBoardTitleContains(keyword, pageable);
	    } else {
	    	logger.debug("확인2");
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
	 * 공통소스 메서드
	 */
    private Page<PurRevBoardDTO> getPurRevBoardDTO(Pageable pageable, Page<PurRevBoard> purRevBoards) {
        List<PurRevBoardDTO> purRevBoardDTOs = new ArrayList<>();

        for (PurRevBoard purRevBoard : purRevBoards) {
        	PurRevBoardDTO result = PurRevBoardDTO.builder()
                    .purRevBoard(purRevBoard)
                    .build();
        	purRevBoardDTOs.add(result);
        }

        return new PageImpl<>(purRevBoardDTOs, pageable, purRevBoards.getTotalElements());
    }
	
    
    
}
