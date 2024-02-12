package com.team.mztelecom.repository;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.team.mztelecom.domain.CustBas;

@Repository
public interface AdminRepository extends JpaRepository<CustBas, Long> {
	@Query(value = "SELECT i FROM CustBas i WHERE i.custId != 'admin'")
	List<CustBas> findAllCustInfo();

	Page<CustBas> findByCustNmContaining(String custNm, Pageable pageable);
}
