package com.team.mztelecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByCustBasId(Long custId);
}
