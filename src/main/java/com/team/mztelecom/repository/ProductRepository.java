package com.team.mztelecom.repository;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.IntmBas;

@Repository
public interface ProductRepository extends JpaRepository<IntmBas, Long> {
	@Query(value = "SELECT i FROM IntmBas i LEFT JOIN FETCH i.intmImgs")
    List<IntmBas> findAllProductsWithImages();
	
	// 상품 페이지 정렬
	Page<IntmBas> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<IntmBas> findAllByOrderByIntmPriceAsc(Pageable pageable);
    Page<IntmBas> findAllByOrderByIntmPriceDesc(Pageable pageable);
	
	/**
	 * 상품 조회 - 김시우
	 * (마이페이지의 찜한 상품에서 사용)
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "select * from intm_bas where id = :id", nativeQuery = true)
	IntmBas getIntmBas(@Param("id")Long id);
}
