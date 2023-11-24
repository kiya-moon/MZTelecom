package com.team.mztelecom.repository;

import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.CustBas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CustRepository extends JpaRepository<CustBas, Long>  {

	/**
	 * 아이디 찾기 repository - 김시우
	 * @param custNm
	 * @param custBirth
	 * @param custEmail
	 * @return
	 */
	@Query(value = "select * from cust_bas where cust_nm = :custNm and cust_birth = :custBirth and cust_email = :custEmail", nativeQuery = true)
	List<CustBas> findById(@Param("custNm")String custNm, @Param("custBirth")String custBirth, @Param("custEmail")String custEmail);
	
	/**
	 * 비밀번호 찾기 repository - 김시우
	 * @param custId
	 * @param custBirth
	 * @param custEmail
	 * @return
	 */
	@Query(value = "select * from cust_bas where cust_id = :custId and cust_birth = :custBirth and cust_email = :custEmail", nativeQuery = true)
	List<CustBas> findByPw(@Param("custId")String custId, @Param("custBirth")String custBirth, @Param("custEmail")String custEmail);
	
}
