package com.team.mztelecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.CustWish;

import jakarta.transaction.Transactional;

@Repository
public interface CustWishRepository extends JpaRepository<CustWish, Long> {

	/**
	 * 찜하기 취소 - 김시우
	 * 
	 * @param intmBasId
	 * @param CustBasId
	 */
	@Modifying
	@Transactional
	@Query(value = "delete from cust_wish where intm_bas_id = :intmBasId and cust_bas_id = :custBasId", nativeQuery = true)
	void deleteByCustandIntm(@Param("intmBasId")Long intmBasId, @Param("custBasId")Long CustBasId);
	
	
	/**
	 * 찜하기 유무 값 감소처리 - 김시우
	 * 
	 * @param intmBasId
	 */
	@Modifying
	@Transactional
	@Query(value = "UPDATE intm_bas SET wish_cnt = CASE WHEN wish_cnt > 0 THEN wish_cnt - 1 ELSE 0 END WHERE id = :intmBasId", nativeQuery = true)
	void reduceLike(@Param("intmBasId")Long intmBasId);
	
	/**
	 * 찜하기 유무 값 증가처리 - 김시우
	 * 
	 * @param intmBasId
	 * @return
	 */
	@Modifying
	@Transactional
	@Query(value = "update intm_bas set wish_cnt = wish_cnt + 1 where id = :intmBasId", nativeQuery = true)
	int increaseLike(@Param("intmBasId")Long intmBasId);
	
	/**
	 * 찜하기 체크 확인 - 김시우
	 * 
	 * @param intmBasId
	 * @param CustBasId
	 * @return
	 */
	@Query(value = "select count(*) from cust_wish where intm_bas_id = :intmBasId and cust_bas_id = :custBasId", nativeQuery = true)
	int wishChk(@Param("intmBasId")Long intmBasId, @Param("custBasId")Long CustBasId);
	
	/**
	 * 찜하기 한 상품 마이페이지에서 조회 - 김시우
	 * 
	 * @param id
	 * @return
	 */
	@Query(value = "select a.id from intm_bas a where 1=1 and a.id in (select b.intm_bas_id from cust_wish b where b.cust_bas_id = :custBasId)", nativeQuery = true)
	List<Long> getWish(@Param("custBasId")Long id);
	
	
}
