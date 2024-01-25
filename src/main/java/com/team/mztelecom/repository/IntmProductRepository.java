package com.team.mztelecom.repository;


import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.IntmProduct;

@Repository
public interface IntmProductRepository extends JpaRepository<IntmProduct, Long> {
	@Query(value = "select * from intm_product where intm_model_id = :intmModelId", nativeQuery = true)
	List<IntmProduct> findModel(@Param("intmModelId") String intmModelId);
}
