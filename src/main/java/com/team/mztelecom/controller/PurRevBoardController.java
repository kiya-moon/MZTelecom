package com.team.mztelecom.controller;

import java.io.File;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.team.mztelecom.domain.PurRevBoard;
import com.team.mztelecom.dto.PurRevAttachmentDTO;
import com.team.mztelecom.dto.PurRevBoardDTO;
import com.team.mztelecom.service.CustService;
import com.team.mztelecom.service.PurRevAttachmentService;
import com.team.mztelecom.service.PurRevBoardService;
import com.team.util.MD5Generator;
import com.team.util.StringUtil;
import com.team.util.Utiles;

@Controller
public class PurRevBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(PurRevBoardController.class);
	
	@Autowired
	PurRevBoardService purRevBoardService;
	
	@Autowired
	PurRevAttachmentService purRevAttachmentService;
	
	@Autowired
	CustService custService;
	
	// 글 작성 페이지
	@PostMapping(value = "/purRevWrite")
	public String write(@RequestParam("selectedCategory")String selectedCategory
						,@RequestParam("writer") String writer
						,@RequestParam("title")String title
						,@RequestParam("contents")String contents
						,@RequestParam("file") MultipartFile file
						,Model model) {
		
		logger.debug("category :: " + selectedCategory);
		logger.debug("contents :: " + contents);
		logger.debug("fileName :: " + StringUtil.toString(file));
		
		try 
		{
			String origFileName = "";
			String filename = "";
			String savePath = "";
			String filePath	="";
			
			if(!Utiles.isNullOrEmpty(file.getOriginalFilename())) {
				origFileName	= file.getOriginalFilename();
				filename		= new MD5Generator(origFileName).toString();
				// 실행되는 위치의 'files' 폴더에 파일이 저장됩니다.
				savePath 	= System.getProperty("user.dir") + "\\files";
				
				// 파일이 저장되는 폴더가 없으면 폴더를 생성
				if(!new File(savePath).exists())
				{
					try 
					{
						new File(savePath).mkdir();
					}
					catch(Exception e)
					{
						e.getStackTrace();
					}
				}
				
				filePath		= savePath + "\\" + filename;
				file.transferTo(new File(filePath));
			}
			
			PurRevAttachmentDTO purRevAttachmentDTO = new PurRevAttachmentDTO();
			purRevAttachmentDTO.setOrigFileName(origFileName);
			purRevAttachmentDTO.setFileName(filename);
			purRevAttachmentDTO.setFilePath(filePath);
			
			// 첨부파일 저장 및 id값 리턴
			Long attachmentId	=  purRevAttachmentService.saveAttachment(purRevAttachmentDTO);
			logger.debug("첨부파일까지 확인");
			
			// 구매후기 저장 처리
			PurRevBoardDTO purRevBoardDTO = new PurRevBoardDTO();
			
			purRevBoardDTO.setIntmNm(selectedCategory);				// 상품카테고리
			purRevBoardDTO.setBoardTitle(title);					// 글제목
			purRevBoardDTO.setBoardDetail(contents);				// 글내용
			purRevBoardDTO.setAttachmentId(attachmentId);			// 첨부파일
			purRevBoardDTO.setWriter(writer);						// 작성자
			
			// 글작성 페이지 저장 서비스
			purRevBoardService.writePurRev(purRevBoardDTO);
			logger.debug("글작성 저장까지 확인");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		logger.debug("보내기 완료");
		
		return "redirect:/purRevBoard";
	}
	
	
	
	/**
	 * 작성된 글 수정 - 김시우
	 * 
	 * @return
	 */
	@PostMapping(value="/purRevView/{id}")
	public String updatePurRev(Model model, Principal principal, @PathVariable Long id, @ModelAttribute PurRevBoardDTO purRevBoardDTO) {
		
		logger.debug("수정 확인");
		logger.debug("purRevBoardDTO :: " + StringUtil.toString(purRevBoardDTO));
		
		
		return "redirect:/purRevView/{id}";
	}
	
	
	
}
