package com.team.mztelecom.service;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.team.mztelecom.domain.AllStatus;
import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.IntmProduct;
import com.team.mztelecom.domain.Orders;
import com.team.mztelecom.domain.PurRevBoard;
import com.team.mztelecom.dto.CustBasDTO;
import com.team.mztelecom.dto.OrdersDTO;
import com.team.mztelecom.repository.CustRepository;
import com.team.mztelecom.repository.OrderRepository;
import com.team.util.Utiles;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	private final OrderRepository orderRepository;
	
	private final CustRepository custRepository;
	
	/*
	 * public Orders autoOrder(CustBas custBas, IntmProduct intmProduct) {
	 * 
	 * OrdersDTO orderDTO = OrdersDTO.builder() .intmKorNm(null) .price(null)
	 * .orderUid(UUID.randomUUID().toString()) .custBas(custBas)
	 * .intmProduct(intmProduct) .status(AllStatus.READY) .paymentUid("임시 결제 고유 번호")
	 * .build();
	 * 
	 * Orders order = orderDTO.toEntity();
	 * 
	 * return orderRepository.save(order); }
	 */
	
	
	public void updateCustAddress (String address) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String userId = authentication.getName();
		
		Optional<CustBas> custBas = custRepository.findByCustId(userId);

        if (!Utiles.isNullOrEmpty(custBas)) {
        	CustBas custBasEntity = custBas.get();
        	
            custBasEntity.UpdateAddress(address);

            custRepository.save(custBasEntity);
        }
    }
}
