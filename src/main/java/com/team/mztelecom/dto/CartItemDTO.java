package com.team.mztelecom.dto;


import com.team.mztelecom.domain.CartItem;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemDTO {
	Long	id;
	int 	count;
	
    
	public CartItemDTO(Long id, int count) {
		this.id = id;
		this.count = count;
	}
	
	public CartItem toEntity() {
		return CartItem.builder()
				.id(id)
				.count(count)
				.build();
				
	}
	
}
