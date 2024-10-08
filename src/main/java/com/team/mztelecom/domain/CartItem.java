package com.team.mztelecom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CartItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="intmBas_id")
    private IntmBas intmBas;
	
    @Builder
	public CartItem(Long id, Cart cart, IntmBas intmBas) {

		this.id = id;
		this.cart = cart;
		this.intmBas = intmBas;
	}
    
}
