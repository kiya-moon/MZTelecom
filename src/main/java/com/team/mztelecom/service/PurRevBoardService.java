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
import org.springframework.web.multipart.MultipartFile;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.PurRevAttachment;
import com.team.mztelecom.domain.PurRevBoard;
import com.team.mztelecom.dto.CustBasDTO;
import com.team.mztelecom.dto.PurRevAttachmentDTO;
import com.team.mztelecom.dto.PurRevBoardDTO;
import com.team.mztelecom.dto.TemporarySaveDTO;
import com.team.mztelecom.repository.PurRevAttachmentRepository;
import com.team.mztelecom.repository.PurRevBoardRepository;
import com.team.mztelecom.service.PurRevBoardService;
import com.team.util.StringUtil;
import com.team.util.Utiles;

import jakarta.transaction.Transactional;

@Service
public class PurRevBoardService{
	
	private static final Logger logger = LoggerFactory.getLogger(PurRevBoardService.class);
	
	@Autowired
	TemporarySaveDTO temporarySaveDTO;
	
	@Autowired
	PurRevBoardRepository purRevBoardRepository;
	
	@Autowired
	CustService custService;
	
	@Autowired
	AttachmentService purRevAttachmentService;
	
	@Autowired
	PurRevAttachmentRepository purRevAttachmentRepository;

	/**
	 * 검색 안했을 시 목록 보여주는 서비스 - 김시우
	 * 
	 * @param pageable
	 * @return
	 */
    public Page<PurRevBoardDTO> PurRevBoardList(Pageable pageable) {
		
		logger.debug("검색x 목록 노출");
		
        Page<PurRevBoard> purRevBoards = purRevBoardRepository.findAll(pageable);
        
	    List<PurRevBoard> temp = new ArrayList<>();
	    
	    temp.addAll(purRevBoards.getContent());
	    
	    logger.debug("서비스단에서 확인 ::: " + StringUtil.toString(temp));
        
        return getPurRevBoardDTO(pageable, purRevBoards);
    }
	
    /**
     * 검색 시 목록 보여주는 서비스 - 김시우
     * 
     * @param keyword
     * @param pageable
     * @param catgo
     * @return
     */
	public Page<PurRevBoardDTO> searchingPurRevBoard(String keyword, Pageable pageable, String catgo) {
	    logger.debug("검색o 목록 노출");

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
	 * 구매후기 작성시 저장처리 - 김시우
	 * 
	 * @param inPurRevBoardDTO
	 */
	@Transactional
	public void writePurRev(PurRevBoardDTO inPurRevBoardDTO) {
		logger.debug("구매후기 작성 저장 서비스");
		
		// 작성일자
	    Date nowDate = new Date();
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
	    String date = simpleDateFormat.format(nowDate);
		
		PurRevBoard	inpurRevBoard;
		
		// 작성자 정보 가지고 오기
		List<CustBas> inCustBas = new ArrayList<>();
		inCustBas = custService.getId(inPurRevBoardDTO.getWriter());
		
		logger.debug("getIntmNm ::::" + inPurRevBoardDTO.getIntmNm());
		
		// 첨부파일 저장 및 조회
		List<PurRevAttachment> outPurRevAttachment = purRevAttachmentService.saveAttachment(inPurRevBoardDTO.getPurRevAttachmentDTO());
		
		if (!Utiles.isNullOrEmpty(outPurRevAttachment)) {
		    inpurRevBoard = PurRevBoard.builder()
		            .intmNm(inPurRevBoardDTO.getIntmNm())
		            .boardTitle(inPurRevBoardDTO.getBoardTitle())
		            .boardDate(date)
		            .boardDetail(inPurRevBoardDTO.getBoardDetail())
		            .writer(inPurRevBoardDTO.getWriter())
		            .custBas(inCustBas.get(0))
		            .build();

		    for (PurRevAttachment attachment : outPurRevAttachment) {
		        inpurRevBoard.addPurRevAttachment(attachment);
		    }

		    logger.debug("inpurRevBoard ::::" + StringUtil.toString(inpurRevBoard));
		    purRevBoardRepository.save(inpurRevBoard);
		}    
		else 
		{
			inpurRevBoard = PurRevBoard.builder()
					.intmNm(inPurRevBoardDTO.getIntmNm())
					.boardTitle(inPurRevBoardDTO.getBoardTitle())
					.boardDate(date)
					.boardDetail(inPurRevBoardDTO.getBoardDetail())
					.writer(inPurRevBoardDTO.getWriter())
					.custBas(inCustBas.get(0))
					.build();
			
			logger.debug("inpurRevBoard ::::" + StringUtil.toString(inpurRevBoard));
			purRevBoardRepository.save(inpurRevBoard);
		}
	}
	

	/**
	 * 구매후기 상세페이지
	 * 
	 * @param id
	 * @return
	 */
	public PurRevBoardDTO purRevView(Long id) {
		logger.debug("구매후기 상세페이지 서비스");
		
		Optional<PurRevBoard> outPurRevBoard = purRevBoardRepository.findById(id);
		
		PurRevAttachmentDTO purRevAttachmentDTO = new PurRevAttachmentDTO();
		List<PurRevAttachmentDTO> inPurRevAttachmentDTO = new ArrayList<PurRevAttachmentDTO>();
		
		CustBasDTO inCustBasDTO = CustBasDTO.builder()
				.custId(outPurRevBoard.get().getCustBas().getCustId())
				.build();
		List<CustBasDTO> inCustBasDTOList = new ArrayList<>();
		inCustBasDTOList.add(inCustBasDTO);
		
		if(!Utiles.isNullOrEmpty(outPurRevBoard.get().getPurRevAttachments())) 
		{
			for(int i = 0; i < outPurRevBoard.get().getPurRevAttachments().size(); i++) 
			{
				purRevAttachmentDTO.setId(outPurRevBoard.get().getPurRevAttachments().get(i).getId());
				purRevAttachmentDTO.setFileName(outPurRevBoard.get().getPurRevAttachments().get(i).getFileName());
				purRevAttachmentDTO.setOrigFileName(outPurRevBoard.get().getPurRevAttachments().get(i).getOrigFileName());
				purRevAttachmentDTO.setFilePath(outPurRevBoard.get().getPurRevAttachments().get(i).getFilePath());
				
				inPurRevAttachmentDTO.add(purRevAttachmentDTO);
			}
		}
		
		PurRevBoardDTO inPurRevBoardDto = PurRevBoardDTO.builder()
				.writer(outPurRevBoard.get().getWriter())
				.intmNm(outPurRevBoard.get().getIntmNm())
				.boardTitle(outPurRevBoard.get().getBoardTitle())
				.boardDetail(outPurRevBoard.get().getBoardDetail())
				.purRevAttachmentDTO(inPurRevAttachmentDTO)
				.custBasDTO(inCustBasDTOList)
				.build();
		
		return inPurRevBoardDto;
	}
	
	/**
	 * 구매후기 수정 - 김시우
	 * 
	 * @param id
	 * @param inPurRevBoardDTO
	 * @param inFile
	 */
	public void purRevUpdate(Long id, PurRevBoardDTO inPurRevBoardDTO, List<MultipartFile> inFile) {
		
		logger.debug("구매후기 수정 서비스");
		
		// 삭제할 이미지 파일 갯수
		int dbFilesCnt = temporarySaveDTO.getDeleteFileId().size();
		logger.debug("dbFilesCnt ::: " + dbFilesCnt);
		// 기존 파일 삭제시
		try 
		{
			// DB에 저장 되어 있는 파일 불러오기 
			List<PurRevAttachmentDTO> dbFilesList = purRevAttachmentService.getAttachment(id);

			for(int i = 0 ; i < dbFilesList.size(); i++) 
			{
				logger.debug("dbFilesList.get(i).getId() :: " + dbFilesList.get(i).getId());
				logger.debug("dbFilesList.get(i).getOrigFileName() :: " + dbFilesList.get(i).getOrigFileName());
				for(int j =0; j < dbFilesCnt; j++) 
				{
					logger.debug("dbFilesList :: " + dbFilesList.get(i).getId() + ", temporarySaveDTO :: " + temporarySaveDTO.getDeleteFileId().get(j));
					// 저장되어 있는 파일과 삭제할 파일 비교
					if(dbFilesList.get(i).getId().equals(temporarySaveDTO.getDeleteFileId().get(j))) 
					{
						purRevAttachmentService.deledById(dbFilesList.get(i).getId());
						break;
					}
				}
			}
		} 
		catch (Exception e) 
		{
			logger.debug("삭제할 파일이 없습니다.");
		}
		
        // DB에 저장되어있는 파일 불러오기 
        List<PurRevAttachmentDTO> dbAttachmentList = purRevAttachmentService.getAttachment(id);
        
        for(int i = 0; i < dbAttachmentList.size(); i++) {
        	logger.debug("dbAttachmentList :: " + StringUtil.toString(dbAttachmentList.get(i)));
        	
        }
        
		// 파일 저장 형식으로 변환
		List<PurRevAttachmentDTO> convertFileList = new ArrayList<>();
        
        // DB에서 조회 했을시 첨부파일이 존재 x
        if(Utiles.isNullOrEmpty(dbAttachmentList)) 
        { 		
        	// 새로 전달되어온 파일이 하나라도 존재
            if(!Utiles.isNullOrEmpty(inFile)) 
            { 
            	convertFileList = purRevAttachmentService.convertFile(inFile);
            }
            else
            {
            	convertFileList = null;
            }
        }
        // DB에서 조회 했을시 첨부파일이 존재할 때
        else
        {  
        	// 새로 전달되어온 파일 
            if(!Utiles.isNullOrEmpty(inFile)) 
            { 
            	
                // DB의 저장되어 있는 파일 추출
                for(PurRevAttachmentDTO purRevAttachmentDTO : dbAttachmentList) 
                {
                    // DB의 파일 원본파일명 얻어오기
                    String dbOrigFileName = purRevAttachmentDTO.getOrigFileName();
                    
                    // dbOrigFileName 이 객체가 inFile 리스트에 포함이 되어 있는지, 포함이 안 되어 있는지 확인
                    if(!inFile.contains(dbOrigFileName))
                    {
                    	convertFileList = purRevAttachmentService.convertFile(inFile);
                    }
                }
            }
            else
            {
            	convertFileList = null;
            }
        }
        
        // 새로운 첨부파일 저장
        List<PurRevAttachment> addAtcmList = new ArrayList<>();
        List<PurRevAttachmentDTO> addAtcmDTOList = new ArrayList<>();
        PurRevBoardDTO outPurRevBoardDTO = null;
        
        	
		addAtcmList = purRevAttachmentService.saveAttachment(convertFileList);
		
		// 첨부파일 저장처리
		for(int i = 0; i < addAtcmList.size(); i++)
		{
			PurRevAttachmentDTO purRevAttachmentDTO = PurRevAttachmentDTO.builder()
					.id(addAtcmList.get(i).getId())
					.fileName(addAtcmList.get(i).getFileName())
					.filePath(addAtcmList.get(i).getFilePath())
					.origFileName(addAtcmList.get(i).getOrigFileName())
					.build();
			addAtcmDTOList.add(purRevAttachmentDTO);
		}
		
		outPurRevBoardDTO = PurRevBoardDTO.builder()
				.id(inPurRevBoardDTO.getId())
				.boardTitle(inPurRevBoardDTO.getBoardTitle())
				.boardDetail(inPurRevBoardDTO.getBoardDetail())
				.purRevAttachmentDTO(addAtcmDTOList)
				.build();
		
		PurRevBoard inPurRevBoard = purRevBoardRepository.findById(outPurRevBoardDTO.getId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
		logger.debug("inPurRevBoard::  " + StringUtil.toString(inPurRevBoard));
		
		for (PurRevAttachment inAttachment : addAtcmList) 
		{
			inPurRevBoard.addPurRevAttachment(inAttachment);
		}
			
		inPurRevBoard.updatePurRev(outPurRevBoardDTO.getBoardTitle(), outPurRevBoardDTO.getBoardDetail(), addAtcmList);
		
	    purRevBoardRepository.save(inPurRevBoard);
	}
	
	/**
	 * 구매후기 게시물 삭제 - 김시우
	 * 
	 * @param id
	 */
    public void removePurRev(Long id) {
    	
    	logger.debug("구매후기 삭제 id :: " + id);
    	purRevBoardRepository.deleteById(id);
    	
    }
    
    /**
     * 공통소스 메서드 - 김시우
     * 
     * @param pageable
     * @param purRevBoards
     * @return
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
        			.writer(purRevBoard.getWriter())
                    .build();
        	purRevBoardDTOs.add(result);
        }

        return new PageImpl<>(purRevBoardDTOs, pageable, purRevBoards.getTotalElements());
    }
    
    
    
}
