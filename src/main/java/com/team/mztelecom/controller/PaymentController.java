package com.team.mztelecom.controller;


import java.io.IOException;
import java.security.Principal;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.team.mztelecom.dto.OrdersDTO;
import com.team.mztelecom.service.OrderService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PaymentController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	private final OrderService orderService;
	
	private IamportClient iamportClient;
	
	
	// 포트원 API 키와 시크릿 키 가져오기
	@Value("${imp.api.key}")
    private String apiKey;
 
    @Value("${imp.api.secretkey}")
    private String secretKey;
    
    // 포트원 클라이언트를 초기화
    @PostConstruct
    public void init() {
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }
    
    // 결제하기
    @PostMapping("/order/payment")
    public ResponseEntity<String> paymentComplete(@RequestBody OrdersDTO ordersDTO, Principal principal) throws IOException {
    	
    	
    	// 주문 번호 문자열
    	String orderNumber = String.valueOf(ordersDTO.getOrderUid());
    	
    	// 상품 정보 설정
    	orderService.setIntmProduct();
        
        try {
        	// 현재 사용자 아이디
            String userId = principal.getName();
            orderService.ordersSave(userId, ordersDTO);
            
            logger.info("결제 성공 : 주문 번호 {}", orderNumber);
            
            // 성공 응답 반환
            return ResponseEntity.ok().build();
            
        } catch (RuntimeException e) {
        	logger.info("주문 상품 환불 진행 : 주문 번호 {}", orderNumber);
        	
        	// 실패 응답 반환
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    // 포트원 결제 정보 검증
    @ResponseBody
    @PostMapping("/validation/{imp_uid}")
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid, @RequestBody Map<String, Object> requestBody, Principal principal) throws IamportResponseException, IOException {
        
    	// 결제 정보
		IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);
        
		String address = (String) requestBody.get("address");
		
		// 고객 주소 업데이트
		orderService.updateCustAddress(address, principal);
		
        logger.info("결제 요청 응답. 결제 내역 - 주문 번호: {}", payment.getResponse().getMerchantUid());
        
        return payment;
    }

}

