package com.team.mztelecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.FAQ;

@Repository
public interface FAQRepository  extends JpaRepository<FAQ, Long>{

}
