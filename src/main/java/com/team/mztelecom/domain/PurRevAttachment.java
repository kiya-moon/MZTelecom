package com.team.mztelecom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PurRevAttachment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
	generator = "SPRING_SEQ_GENERATOR")
	private Long id;
	
	@NotNull
	private String origFileName;
	
	@NotNull
	private String fileName;
	
	@NotNull
	private String filePath;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purRevBoard_id")
	private PurRevBoard purRevBoard;

	@Builder
	public PurRevAttachment(Long id, String origFileName, String fileName, String filePath) {
		this.id = id;
		this.origFileName = origFileName;
		this.fileName = fileName;
		this.filePath = filePath;
	}
	
	public void updateAttachment(String origFileName, String fileName, String filePath) {
        this.origFileName = origFileName;
        this.fileName = fileName;
        this.filePath = filePath;
    }
	
	
}
