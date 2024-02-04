package com.team.mztelecom.dto;

import com.team.mztelecom.domain.QnA;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QnADTO {

	Long 	id;
	String 	category;
	String	email;
	String	details;
	
	
	public QnA toEntity() {
		QnA build = QnA.builder()
				.id(id)
				.category(category)
				.email(email)
				.details(details)
				.build();
				return build;
	}

	@Builder
	public QnADTO(Long id, String category, String email, String details) {
		this.id = id;
		this.category = category;
		this.email = email;
		this.details = details;
	}
	
	
	
	
	
}
