package com.team.mztelecom.dto;


import com.team.mztelecom.domain.CartItem;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
