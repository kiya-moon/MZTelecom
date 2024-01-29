package com.team.mztelecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.SysCdBas;

@Repository
public interface SysCdRepository extends JpaRepository<SysCdBas, Long>{

	@Query(value = "select * from sys_cd_bas where sys_cd_group_id = :sysCdGroupId and sys_cd_id = :sysCdId", nativeQuery = true)
	SysCdBas getSysCd(@Param("sysCdGroupId")String sysCdGroupId, @Param("sysCdId")String sysCdId);
}
