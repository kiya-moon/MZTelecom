package com.team.mztelecom.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
	
	/**
	 * 주문내역 조회 - 김시우
	 * 
	 * @param id
	 * @return
	 */
	List<Orders> findByCustBasId(Long id);
}
