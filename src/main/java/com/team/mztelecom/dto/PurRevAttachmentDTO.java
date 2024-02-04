package com.team.mztelecom.dto;

import com.team.mztelecom.domain.PurRevAttachment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PurRevAttachmentDTO {
	
	Long 	id;
	String	origFileName;
	String	fileName;
	String	filePath;

	
	public PurRevAttachment toEntity() {
		PurRevAttachment build = PurRevAttachment.builder()
									.id(id)
									.origFileName(origFileName)
									.fileName(fileName)
									.filePath(filePath)
									.build();
		return build;
	}

	@Builder
	public PurRevAttachmentDTO(Long id, String origFileName, String fileName, String filePath) {
		this.id = id;
		this.origFileName = origFileName;
		this.fileName = fileName;
		this.filePath = filePath;
	}
	
	
}
