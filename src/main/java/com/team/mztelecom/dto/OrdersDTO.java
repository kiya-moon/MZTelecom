package com.team.mztelecom.dto;

import com.team.mztelecom.domain.AllStatus;
import com.team.mztelecom.domain.Orders;

import lombok.Builder;

public class OrdersDTO {
	Long 		id;
    String 		intmKorNm;		// 단말기 한글 이름
	String 		price;			// 결제 금액
	String 		orderUid; 		// 주문 번호
    Long 		custBas;		// 회원
    Long 		intmProduct;	// 상품
	AllStatus 	status;			// 상태
	String 		paymentUid;		// 결제 고유 번호
	
	@Builder
    public OrdersDTO(Long id, String intmKorNm, String price, String orderUid,
    		Long custBas, Long intmProduct, AllStatus status, String paymentUid) {
		
        this.id = id;
        this.intmKorNm = intmKorNm;
        this.price = price;
        this.orderUid = orderUid;
        this.custBas = custBas;
        this.intmProduct = intmProduct;
        this.status = status;
        this.paymentUid = paymentUid;
    }
	
	public Orders toEntity() {

        return Orders.builder()
                .id(id)
                .intmKorNm(intmKorNm)
                .price(price)
                .orderUid(orderUid)
                .status(status)
                .paymentUid(paymentUid)
                .build();
    }
	
}
