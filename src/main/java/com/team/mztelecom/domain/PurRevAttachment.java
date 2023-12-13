package com.team.mztelecom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurRevAttachment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String origFileName;
	
	@NotNull
	private String fileName;
	
	@NotNull
	private String filePath;

	@Builder
	public PurRevAttachment(Long id, String origFileName, String fileName, String filePath) {
		this.id = id;
		this.origFileName = origFileName;
		this.fileName = fileName;
		this.filePath = filePath;
	}
	
	
}
