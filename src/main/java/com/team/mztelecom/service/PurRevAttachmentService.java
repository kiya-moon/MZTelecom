package com.team.mztelecom.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.team.mztelecom.domain.PurRevAttachment;
import com.team.mztelecom.dto.PurRevAttachmentDTO;
import com.team.mztelecom.repository.PurRevAttachmentRepository;
import com.team.util.StringUtil;
import com.team.util.Utiles;

import jakarta.transaction.Transactional;

@Service
public class PurRevAttachmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(PurRevAttachmentService.class);
	
	@Autowired
	private PurRevAttachmentRepository purRevAttachmentRepository;
	
	@Value("${file.dir}")
	private String fileDir;
	
	/**
	 * 첨부파일 저장	- 김시우
	 * 
	 * @param purRevAttachmentDTO
	 * @return
	 */
	@Transactional
	public List<PurRevAttachment> saveAttachment(List<PurRevAttachmentDTO> inFiles) 
	{
		
		List<PurRevAttachment> outPurRevAttachment = new ArrayList<>();
		
		// 첨부파일이 있을 경우에만 저장처리
		try 
		{
			
			if(inFiles.size() > 0) 
			{
				logger.debug("inList.size() ::: " + inFiles.size());
				
				for(int i = 0 ; i < inFiles.size(); i++) 
				{
					
					PurRevAttachmentDTO purRevAttachmentDTO = PurRevAttachmentDTO.builder()
							.origFileName(inFiles.get(i).getOrigFileName())
							.fileName(inFiles.get(i).getFileName())
							.filePath(inFiles.get(i).getFilePath())
							.build();
					
					PurRevAttachment inPurRevAttachment = purRevAttachmentDTO.toEntity();
					
					outPurRevAttachment.add(purRevAttachmentRepository.save(inPurRevAttachment));
					logger.debug("AttachmentId ::: " + StringUtil.toString(outPurRevAttachment.get(i)));
					
				}
			}
			
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
		
		return outPurRevAttachment;
	}
	
	/**
	 * 첨부파일 조회	- 김시우
	 * 
	 * @param
	 * @return
	 */
	public List<PurRevAttachmentDTO> getAttachment(Long id) 
	{
		List<PurRevAttachment> purRevAttachment = purRevAttachmentRepository.findByPurRevBoardId(id);
		
		List<PurRevAttachmentDTO> outPurRevAttachmentDTO = new ArrayList<>();
		
		for(PurRevAttachment inPurRevAttachment : purRevAttachment) 
		{
			
			PurRevAttachmentDTO purRevAttachmentDTO = PurRevAttachmentDTO.builder()
					.id(inPurRevAttachment.getId())
					.origFileName(inPurRevAttachment.getOrigFileName())
					.fileName(inPurRevAttachment.getFileName())
					.filePath(inPurRevAttachment.getFilePath())
					.build();
			
			outPurRevAttachmentDTO.add(purRevAttachmentDTO);
		}
		
		return outPurRevAttachmentDTO;
	}
	
	/**
	 * 첨부파일 불러오기 - 김시우
	 * 
	 * @param id
	 * @return
	 */
	public Optional<PurRevAttachment> findById(Long id) {
		
		logger.debug("파일 조회 id :: " + id);
    
		return purRevAttachmentRepository.findById(id);
	}
	
	/**
	 * 첨부파일 삭제 - 김시우
	 * 
	 * @param id
	 */
	public void deledById(Long id) {
		
		logger.debug("파일 삭제 id :: " + id);
		
		purRevAttachmentRepository.deleteById(id);
	}

	
	/**
	 * 첨부파일 세팅 - 김시우
	 * 
	 * @param inFiles
	 * @return
	 */
    public List<PurRevAttachmentDTO> convertFile(List<MultipartFile> inFiles) {
    	
		// 반환할 파일 리스트
        List<PurRevAttachmentDTO> fileList = new ArrayList<>();
        
        try 
        {
	        // 전달되어 온 파일이 존재할 경우
        	if(!Utiles.isNullOrEmpty(inFiles.get(0).getOriginalFilename())) 
	        { 
	
	            // 파일을 저장할 세부 경로 지정
	            String path = fileDir;
	    		UUID uuid = UUID.randomUUID();
	            File file = new File(path);
  
	            // 디렉터리가 존재하지 않을 경우
	            if(!file.exists()) 
	            {
	                boolean wasSuccessful = file.mkdirs();
		            // 디렉터리 생성에 실패했을 경우
		            if(!wasSuccessful)
		            {
		                logger.debug("file: was not successful");
		            }
	            }

	            // 다중 파일 처리
	            for(MultipartFile multipartFile : inFiles) 
	            {
  
                    // 파일명 중복 피하고자 uuid 설정
                    String new_file_name = uuid + "_" + multipartFile.getOriginalFilename();
                    
                    PurRevAttachmentDTO inPurRevAttachmentDTO = PurRevAttachmentDTO.builder()
                    		.origFileName(multipartFile.getOriginalFilename())
                    		.fileName(new_file_name)
                    		.filePath(path + new_file_name)
                    		.build();
                
                    // 생성 후 리스트에 추가
                    fileList.add(inPurRevAttachmentDTO);
  
                    // 업로드 한 파일 데이터를 지정한 파일에 저장
                    file = new File(path, new_file_name);
                    multipartFile.transferTo(file);
                
                    // 파일 권한 설정(쓰기, 읽기)
                    file.setWritable(true);
                    file.setReadable(true);
            }
        }
    	
        }
        catch (Exception e) 
        {
			// TODO: handle exception
		}
        
        return fileList;
    }
}
