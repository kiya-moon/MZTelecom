package com.team.mztelecom.dto;


import com.team.mztelecom.domain.Cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDTO {
	Long 	id;
	
    
	public CartDTO(Long id) {
		this.id = id;
	}
	
	public Cart toEntity() {
		return Cart.builder()
				.id(id)
				.build();
				
	}

}
