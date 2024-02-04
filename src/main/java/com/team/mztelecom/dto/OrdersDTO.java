package com.team.mztelecom.dto;


import java.time.LocalDate;

import com.team.mztelecom.domain.AllStatus;
import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.IntmProduct;
import com.team.mztelecom.domain.Orders;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrdersDTO {
	Long 		id;
    String 		intmKorNm;		// 단말기 한글 이름
	String 		price;			// 결제 금액
	String 		orderUid; 		// 주문 번호
    CustBas 	custBas;		// 회원
    IntmProduct intmProduct;	// 상품
	AllStatus 	status;			// 상태
	String 		paymentUid;		// 결제 고유 번호
	LocalDate 	paymentDate;	// 결제 일자
	
	@Builder
    public OrdersDTO(Long id, String intmKorNm, String price, String orderUid,
    		CustBas custBas, IntmProduct intmProduct, AllStatus status, String paymentUid, LocalDate paymentDate) {
		
        this.id = id;
        this.intmKorNm = intmKorNm;
        this.price = price;
        this.orderUid = orderUid;
        this.custBas = custBas;
        this.intmProduct = intmProduct;
        this.status = status;
        this.paymentUid = paymentUid;
        this.paymentDate = paymentDate;
    }
	
	public Orders toEntity() {

        return Orders.builder()
                .id(id)
                .intmKorNm(intmKorNm)
                .price(price)
                .orderUid(orderUid)
                .custBas(custBas)
                .intmProduct(intmProduct)
                .status(status)
                .paymentUid(paymentUid)
                .paymentDate(paymentDate)
                .build();
    }
	
}
