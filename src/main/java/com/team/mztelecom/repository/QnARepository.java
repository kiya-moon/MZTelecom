package com.team.mztelecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.QnA;

@Repository
public interface QnARepository  extends JpaRepository<QnA, Long>{
	
}
