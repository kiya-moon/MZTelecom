package com.team.mztelecom.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.dto.CustBasDTO;

import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface CustRepository extends JpaRepository<CustBas, Long>  , JpaSpecificationExecutor<CustBas> {

	/**
	 * 아이디, 비밀번호 찾기시 / 회원 Long id 조회시 - 김시우
	 * 
	 * @param custId
	 * @param custNm
	 * @param custBirth
	 * @param custEmail
	 * @return
	 */
    default List<CustBas> findByDynamicQuery(String custId, String custNm, String custBirth, String custEmail) {
        return findAll((Specification<CustBas>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 여기서는 null이 아닌 경우에 대한 검색 조건을 추가합니다.
            if (custId != null) {
            	predicates.add(criteriaBuilder.equal(root.get("custId"), custId));
            }
            if (custNm != null) {
                predicates.add(criteriaBuilder.equal(root.get("custNm"), custNm));
            }

            if (custBirth != null) {
                predicates.add(criteriaBuilder.equal(root.get("custBirth"), custBirth));
            }

            if (custEmail != null) {
                predicates.add(criteriaBuilder.equal(root.get("custEmail"), custEmail));
            }

            // AND 조건으로 모든 검색 조건을 결합합니다.
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
	
	/**
	 * 비밀번호 찾기 이메일 발송 repository - 김시우
	 * @param custPw
	 * @param custId
	 */
	@Modifying
	@Transactional
	@Query(value = "update cust_bas set cust_password = :custPw where cust_id = :custId", nativeQuery = true)
	void updatePw(@Param("custPw")String custPw, @Param("custId")String custId);
	
	/**
	 * custId로 사용자 정보 조회 repository - 문기연
	 * @param custId
	 * @return
	 */
	 
	 // 아이디 존재여부 확인. @Query를 줘야 하나?? 
//	 boolean existsById(String custId);
	 
	/**
	 * 회원가입 repository - 문기연
	 * @param custId
	 * @return
	 */
//	 Long save(CustBasDTO request); 
	
	
	/* 로그인 - 박지윤 */
	Optional<CustBas> findByCustId(String custId);
}