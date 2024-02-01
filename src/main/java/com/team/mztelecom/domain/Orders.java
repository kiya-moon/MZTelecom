package com.team.mztelecom.domain;

import java.time.LocalDate;

import com.team.mztelecom.dto.OrdersDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
    private String intmKorNm;			// 단말기 한글 이름
	
	@NotNull
	private String price;				// 결제 금액
	
	@NotNull
	private String orderUid; 			// 주문 번호
	
	@ManyToOne
    @JoinColumn(name = "custBas_id")
    private CustBas custBas;			// 회원
	
	@ManyToOne
    @JoinColumn(name = "intmProduct_id")
    private IntmProduct intmProduct;	// 상품
	
	private AllStatus status;			// 상태

	private String paymentUid; 			// 결제 고유 번호
	
	private LocalDate paymentDate;			// 결제 일자

	
	@Builder
	public Orders(Long id, String intmKorNm, String price, String orderUid,
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
	
	// entity -> dto
	public OrdersDTO toDTO() {
        return OrdersDTO.builder()
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
