package com.team.mztelecom.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.IntmBas;

@Repository
public interface ProductRepository extends JpaRepository<IntmBas, Long> {

}
