package com.team.mztelecom.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.domain.IntmImg;

@Repository
public interface ImgRepository extends JpaRepository<IntmImg, Long> {
	List<IntmImg> findAll();
	
	Optional<IntmImg> findByIntmBas(IntmBas intmBas);
	
	Optional<IntmImg> findByIntmBasId(Long intmBasId);
}
