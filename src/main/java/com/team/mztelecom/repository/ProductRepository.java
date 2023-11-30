package com.team.mztelecom.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.IntmBas;

@Repository
public interface ProductRepository extends JpaRepository<IntmBas, Long> {
	@Query(value = "SELECT i FROM IntmBas i LEFT JOIN FETCH i.intmImgs")
    List<IntmBas> findAllProductsWithImages();
}
