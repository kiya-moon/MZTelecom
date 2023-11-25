package com.team.mztelecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team.mztelecom.domain.CustBas;

public interface UserRepository extends JpaRepository<CustBas, Integer>{
	
	boolean existsByCustId(String custId);

	CustBas findByCustId(String custId);
}
