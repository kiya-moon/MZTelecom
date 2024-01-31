package com.team.mztelecom.dto;

import com.team.mztelecom.domain.FAQ;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FAQDTO {

	Long id;
	String titleFAQ;
	String detailFAQ;
	
	@Builder
	public FAQDTO(Long id, String titleFAQ, String detailFAQ) {
		this.id = id;
		this.titleFAQ = titleFAQ;
		this.detailFAQ = detailFAQ;
	}
	
	
	public FAQ toEntity() {
		FAQ build = FAQ.builder()
					.id(id)
					.titleFAQ(titleFAQ)
					.detailFAQ(detailFAQ)
					.build();
			return build;
	}
	
	
	
}
