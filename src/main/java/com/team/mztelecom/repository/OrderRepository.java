package com.team.mztelecom.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.Orders;

import jakarta.transaction.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
	
	/**
	 * 주문내역 조회 - 김시우
	 * 
	 * @param id
	 * @return
	 */
	List<Orders> findByCustBasId(Long id);

	/**
	 * 주문내역 삭제 - 문기연
	 * @param id
	 */
	@Transactional
    @Modifying
    @Query(value = "DELETE FROM Orders i WHERE i.custBas = :cust")
	void deleteByCustBas(@Param("cust") CustBas cust);
}
