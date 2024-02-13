package com.team.mztelecom.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.team.mztelecom.domain.PurRevAttachment;
import com.team.mztelecom.dto.IntmImgDTO;
import com.team.mztelecom.dto.PurRevAttachmentDTO;
import com.team.mztelecom.repository.PurRevAttachmentRepository;
import com.team.util.StringUtil;
import com.team.util.Utiles;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AttachmentService {
	
	private static final Logger logger = LoggerFactory.getLogger(AttachmentService.class);
	
	private final PurRevAttachmentRepository purRevAttachmentRepository;
	
	@Value("${file.dir}")
	private String fileDir;
	
	@Value("${img.dir}")
	private String imgDir;
	
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
			logger.debug("에러 발생 : " + e);
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
		logger.debug("첨부파일 조회 Start");
		
		PurRevAttachmentDTO inDTO = PurRevAttachmentDTO.builder()
										.id(id)
										.build();
		
		PurRevAttachment inEntity = inDTO.toEntity();
		
		List<PurRevAttachment> purRevAttachment = purRevAttachmentRepository.findByPurRevBoardId(inEntity.getId());
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
	 * html에 첨부파일 이미지 경로를 위해 첨부파일 조회 - 김시우
	 * 
	 * @param id
	 * @return
	 */
	public Optional<PurRevAttachment> findById(Long id) {
		
		logger.debug("파일 조회 id :: " + id);
		
		PurRevAttachmentDTO inDTO = PurRevAttachmentDTO.builder()
										.id(id)
										.build();
		PurRevAttachment inEntity = inDTO.toEntity();
    
		return purRevAttachmentRepository.findById(inEntity.getId());
	}
	
	/**
	 * 첨부파일 삭제 - 김시우
	 * 
	 * @param id
	 */
	public void deledById(Long id) {
		
		logger.debug("파일 삭제 id :: " + id);
		
		PurRevAttachmentDTO inDTO = PurRevAttachmentDTO.builder()
									.id(id)
									.build();
		PurRevAttachment inEntity = inDTO.toEntity();
		
		Optional<PurRevAttachment> outList = purRevAttachmentRepository.findById(inEntity.getId());
		
		PurRevAttachment outEntity = outList.get();
		
		// 폴더에 저장된 이미지 삭제
		deleteFile(outEntity.getFilePath());
		
		// 폴더에 저장된 이미지 삭제 후 DB에서 삭제
		purRevAttachmentRepository.deleteById(inEntity.getId());
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
			logger.debug("에러 발생 :: " + e);
		}
        
        return fileList;
    }
    
    public List<IntmImgDTO> addImages(MultipartFile imgfiles, MultipartFile imgDetailfiles, String addName) {
    	
        List<IntmImgDTO> imgesAdd = new ArrayList<>();
        
        try {
        	
        	if ((imgfiles != null && !Utiles.isNullOrEmpty(imgfiles.getOriginalFilename())) || 
        		    (imgDetailfiles != null && !Utiles.isNullOrEmpty(imgDetailfiles.getOriginalFilename()))) {
        		// 파일을 저장할 세부 경로 지정
	            String path = imgDir;
	    		UUID uuid = UUID.randomUUID();
	    		File files = new File(path);
	    		
	            // 디렉터리가 존재하지 않을 경우
	            if(!files.exists()) {
	                boolean wasSuccessful = files.mkdirs();
		            // 디렉터리 생성에 실패했을 경우
		            if(!wasSuccessful)
		            {
		                logger.debug("file: was not successful");
		            }
	            }

                    // 파일명 중복 피하고자 uuid 설정
                    String new_imgfiles_name;
                    String new_imgDetailfiles_name;
                    IntmImgDTO IntmImgDTOs;
                    File imgfile;
                    File imgdetailfile;
                    
                    /*
                     * 들어온 값에 따라 IntmImgDTO 세팅 - 김시우 
                     */
                    if(!Utiles.isNullOrEmpty(imgfiles) && !Utiles.isNullOrEmpty(imgDetailfiles))
                    {
                        new_imgfiles_name = uuid + "_" + imgfiles.getOriginalFilename();
                    	new_imgDetailfiles_name = uuid + "_" + imgDetailfiles.getOriginalFilename();
                    			
                    	IntmImgDTOs = IntmImgDTO.builder()
                    			.intmNm(addName)
                    			.imgName(imgfiles.getOriginalFilename())
                    			.imgDetailNm(imgDetailfiles.getOriginalFilename())
                    			.imgPath(path + new_imgfiles_name)
                    			.imgDetailPath(path + new_imgDetailfiles_name)
                    			.build();
                    	
                        // 업로드 한 파일 데이터를 지정한 파일에 저장
                        imgfile = new File(path, new_imgfiles_name);
                        imgfiles.transferTo(imgfile);
                        
                        // 파일 권한 설정(쓰기, 읽기)
                        imgfile.setWritable(true);
                        imgfile.setReadable(true);
                        
                        
                        imgdetailfile = new File(path, new_imgDetailfiles_name);
                        imgDetailfiles.transferTo(imgdetailfile);
                        
                        // 파일 권한 설정(쓰기, 읽기)
                        imgdetailfile.setWritable(true);
                        imgdetailfile.setReadable(true);
                    	
                    }
                   	else if(!Utiles.isNullOrEmpty(imgfiles))	// addImageDetail만 존재
                    {
                    	new_imgfiles_name = uuid + "_" + imgfiles.getOriginalFilename();
                    	
                    	IntmImgDTOs = IntmImgDTO.builder()
                    			.intmNm(addName)
                    			.imgName(imgfiles.getOriginalFilename())
                    			.imgPath(path + new_imgfiles_name)
                    			.build();
                    	
                        // 업로드 한 파일 데이터를 지정한 파일에 저장
                        imgfile = new File(path, new_imgfiles_name);
                        imgfiles.transferTo(imgfile);
                        
                        // 파일 권한 설정(쓰기, 읽기)
                        imgfile.setWritable(true);
                        imgfile.setReadable(true);
                    }
                   	else if(!Utiles.isNullOrEmpty(imgDetailfiles))	//	둘다 존재
                    {
                    	
                        
                    	new_imgDetailfiles_name = uuid + "_" + imgDetailfiles.getOriginalFilename();
                    	
                    	IntmImgDTOs = IntmImgDTO.builder()
                    			.intmNm(addName)
                    			.imgDetailNm(imgDetailfiles.getOriginalFilename())
                    			.imgDetailPath(path + new_imgDetailfiles_name)
                    			.build();
                    	
                    	// 업로드 한 파일 데이터를 지정한 파일에 저장
                        imgdetailfile = new File(path, new_imgDetailfiles_name);
                        imgDetailfiles.transferTo(imgdetailfile);
                        
                        // 파일 권한 설정(쓰기, 읽기)
                        imgdetailfile.setWritable(true);
                        imgdetailfile.setReadable(true);

                    	
                    }
                    else
                    {
                    	return imgesAdd;
                    }
                    
                    // 생성 후 리스트에 추가
                    imgesAdd.add(IntmImgDTOs);
  
        	}
			
		} catch (Exception e) {
			logger.debug("에러 발생 :: " + e );
		}
    	
    	return imgesAdd;
    }
	    
    public void deleteFile(String filePath) {
        try {
            // 주어진 파일 경로에 있는 파일을 삭제
            Files.delete(Path.of(filePath));
            logger.info("파일을 삭제하였습니다.");
        } catch (IOException e) {
            throw new RuntimeException("파일 삭제 실패: " + filePath, e);
        }
    }
}
