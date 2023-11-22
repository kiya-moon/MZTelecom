package com.team.mztelecom.repository;

import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.CustBas;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CustRepository extends JpaRepository<CustBas, Long>  {

}
