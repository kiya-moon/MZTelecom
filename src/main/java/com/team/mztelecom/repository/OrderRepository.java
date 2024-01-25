package com.team.mztelecom.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
//  Optional<Orders> findOrderAndMember(String orderUid);
}
