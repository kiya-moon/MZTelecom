package com.team.mztelecom.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	CartItem findByCartIdAndIntmBasId(Long cartId, Long IntmBasId);
	
	List<CartItem> findCartItemsByCartId(Long cartId);

}
