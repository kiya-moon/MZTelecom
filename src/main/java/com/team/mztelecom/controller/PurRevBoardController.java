package com.team.mztelecom.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.team.mztelecom.dto.PurRevAttachmentDTO;
import com.team.mztelecom.dto.PurRevBoardDTO;
import com.team.mztelecom.dto.TemporarySaveDTO;
import com.team.mztelecom.service.AttachmentService;
import com.team.mztelecom.service.PurRevBoardService;
import com.team.util.StringUtil;
import com.team.util.Utiles;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PurRevBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(PurRevBoardController.class);
	
	private final TemporarySaveDTO temporarySaveDTO;
	
	private final PurRevBoardService purRevBoardService;
	
	private final AttachmentService attachmentService;
	
	/**
	 * 글 작성 페이지 - 김시우
	 * 
	 * @param selectedCategory
	 * @param writer
	 * @param title
	 * @param contents
	 * @param files
	 * @param model
	 * @return
	 */
	@PostMapping(value = "/purRevWrite")
	public String write(@RequestParam("selectedCategory")String selectedCategory
						,@RequestParam("writer") String writer
						,@RequestParam("title")String title
						,@RequestParam("contents")String contents
						,@RequestParam("files") List<MultipartFile> files
						,Model model) {
		
		List<PurRevAttachmentDTO> inAttachmentDTO = new ArrayList<>();
		
		// 첨부파일 세팅
		if(Utiles.isNullOrEmpty(files)) {
			 files = Collections.emptyList();
		}
		else
		{
			inAttachmentDTO = attachmentService.convertFile(files);
		}
		
		logger.debug("첨부파일까지 세팅 확인");
		
		// 구매후기 저장 처리
		PurRevBoardDTO purRevBoardDTO = PurRevBoardDTO.builder()
										.intmNm(selectedCategory)
										.boardTitle(title)
										.boardDetail(contents)
										.purRevAttachmentDTO(inAttachmentDTO)
										.writer(writer)
										.build();
		
		// 글작성 페이지 저장 서비스
		purRevBoardService.writePurRev(purRevBoardDTO);
		logger.debug("글작성 저장까지 확인");
		
		return "redirect:/purRevBoard";
	}
	
	/**
	 * 작성된 글 수정 - 김시우
	 * 
	 * @param model
	 * @param principal
	 * @param id
	 * @param inPurRevBoardDTO
	 * @return
	 */
	@PostMapping(value="/purRevView/{id}")
	public String updatePurRev(Model model, @PathVariable Long id
								, @ModelAttribute PurRevBoardDTO inPurRevBoardDTO 
								, @RequestPart(value = "files", required = false) List<MultipartFile> files) {
		
		logger.debug("purRevBoardDTO :: " + StringUtil.toString(inPurRevBoardDTO));
		
		if(Utiles.isNullOrEmpty(files)) 
		{
			files = Collections.emptyList();
		}
		
		// 게시글 업데이트
		purRevBoardService.purRevUpdate(id, inPurRevBoardDTO, files);
		
		// 임시저장 초기화
		temporarySaveDTO.clear();
		
		return "redirect:/purRevBoard";
	}
	
	/**
	 * 작성된 글 삭제 - 김시우
	 * 
	 * @param model
	 * @param id
	 * @param inPurRevBoardDTO
	 * @return
	 */
	@PostMapping(value="/purRevView/{id}/remove")
	public String removePurRev(Model model, @PathVariable Long id) {
		
		logger.debug("삭제 확인");
		
		PurRevBoardDTO inPurRevBoardDTO = PurRevBoardDTO.builder()
												.id(id)
												.build();
		
		purRevBoardService.removePurRev(inPurRevBoardDTO);
		
		return "redirect:/purRevBoard";
	}
	
	/**
	 * 파일 수정 임시 저장 - 김시우
	 * 
	 * @param imageId
	 * @return
	 */
	@PostMapping(value = "/purRevView/tempDeleteFile")
	public ResponseEntity<String> tmprySto(@RequestParam("imageId") Long imageId) {
		
		logger.debug("첨부파일 임시 삭제");
		
	    try 
	    {
	    	
	    	logger.debug("imageId :: " + imageId);
	    	// 삭제 할 임시 imgId 저장
	    	temporarySaveDTO.getDeleteFileId().add(imageId);
	        
	        return ResponseEntity.ok("이미지 삭제 성공");
	        
	    } 
	    catch (Exception e) 
	    {
	        logger.error("이미지 삭제 실패: " + e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 삭제 실패");
	    }
		
	}

}
