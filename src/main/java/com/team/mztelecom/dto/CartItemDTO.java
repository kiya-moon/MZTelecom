package com.team.mztelecom.dto;


import com.team.mztelecom.domain.CartItem;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemDTO {
	Long	id;
	
    @Builder
	public CartItemDTO(Long id) {
		this.id = id;
	}
	
	public CartItem toEntity() {
		return CartItem.builder()
				.id(id)
				.build();
				
	}
	
}
