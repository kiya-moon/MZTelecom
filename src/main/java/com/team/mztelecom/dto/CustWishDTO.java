package com.team.mztelecom.dto;

import com.team.mztelecom.domain.CustWish;

public class CustWishDTO {
	
	Long id;
	
    
	public CustWishDTO(Long id) {
		this.id = id;
	}
	
	public CustWish toEntity() {
		return CustWish.builder()
				.id(id)
				.build();
				
	}
}
