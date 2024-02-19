package com.team.mztelecom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FAQ {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String titleFAQ;
	
	@Lob
	private String detailFAQ;

	@Builder
	public FAQ(Long id, String titleFAQ, String detailFAQ) {
		this.id = id;
		this.titleFAQ = titleFAQ;
		this.detailFAQ = detailFAQ;
	}
	
	
	
	
}
