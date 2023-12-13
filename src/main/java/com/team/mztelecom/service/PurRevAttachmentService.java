package com.team.mztelecom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.PurRevAttachment;
import com.team.mztelecom.dto.PurRevAttachmentDTO;
import com.team.mztelecom.repository.PurRevAttachmentRepository;
import com.team.util.Utiles;

import jakarta.transaction.Transactional;

@Service
public class PurRevAttachmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(PurRevAttachmentService.class);
	
	@Autowired
	private PurRevAttachmentRepository purRevAttachmentRepository;
	
	/**
	 * 첨부파일 저장	- 김시우
	 * 
	 * @param purRevAttachmentDTO
	 * @return
	 */
	@Transactional
	public Long saveAttachment(PurRevAttachmentDTO inPurRevAttachmentDTO) {
		
		Long AttachmentId = null;
		
		// 첨부파일이 있을 경우에만 저장처리
		if(!Utiles.isNullOrEmpty(inPurRevAttachmentDTO.getFileName())) 
		{
		
			PurRevAttachmentDTO purRevAttachmentDTO = PurRevAttachmentDTO.builder()
					.origFileName(inPurRevAttachmentDTO.getOrigFileName())
					.fileName(inPurRevAttachmentDTO.getFileName())
					.filePath(inPurRevAttachmentDTO.getFilePath())
					.build();
			
			PurRevAttachment inPurRevAttachment = purRevAttachmentDTO.toEntity();
			
			PurRevAttachment outPurRevAttachment = purRevAttachmentRepository.save(inPurRevAttachment);
			
			AttachmentId = outPurRevAttachment.getId();
			logger.debug("AttachmentId ::: " + AttachmentId);
		}
		
		return AttachmentId;
	}
	
	/**
	 * 첨부파일 조회	- 김시우
	 * 
	 * @param id
	 * @return
	 */
	public PurRevAttachmentDTO getAttachment(Long id) {
		PurRevAttachment purRevAttachment = purRevAttachmentRepository.findById(id).get();
		
		PurRevAttachmentDTO purRevAttachmentDTO = PurRevAttachmentDTO.builder()
													.id(id)
													.origFileName(purRevAttachment.getOrigFileName())
													.fileName(purRevAttachment.getFileName())
													.filePath(purRevAttachment.getFilePath())
													.build();
		
		return purRevAttachmentDTO;
	}

}
