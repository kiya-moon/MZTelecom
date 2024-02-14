package com.team.mztelecom.service;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.AllStatus;
import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.IntmProduct;
import com.team.mztelecom.domain.Orders;
import com.team.mztelecom.dto.CustBasDTO;
import com.team.mztelecom.dto.IntmProductDTO;
import com.team.mztelecom.dto.OrdersDTO;
import com.team.mztelecom.dto.TemporarySaveDTO;
import com.team.mztelecom.repository.CustRepository;
import com.team.mztelecom.repository.IntmProductRepository;
import com.team.mztelecom.repository.OrderRepository;
import com.team.util.StringUtil;
import com.team.util.Utiles;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	private final TemporarySaveDTO temporarySaveDTO;
	
	private final CustService custService;
	
	private final OrderRepository orderRepository;
	
	private final IntmProductRepository intmProductRepository;

	private final CustRepository custRepository;
	
	/**
	 * 사용자 주소 업데이트 - 박지윤
	 * 
	 * @param address
	 * @param principal
	 */
	public void updateCustAddress(String address, Principal principal) {
		
		// 현재 사용자의 아이디
		String userId = principal.getName();
		
		logger.debug("아이디 확인 :: " + userId);

		Optional<CustBas> custBas = custRepository.findByCustId(userId);
		
		// 고객 정보가 존재하는 경우 주소 업데이트
		if (!Utiles.isNullOrEmpty(custBas)) {
			
			CustBas custBasEntity = custBas.get();

			custBasEntity.UpdateAddress(address);

			custRepository.save(custBasEntity);
		}
	}
	
	/**
	 * 주문 저장처리 - 박지윤
	 * 
	 * @param userId
	 * @param ordersDTO
	 */
	public void ordersSave(String userId, OrdersDTO ordersDTO) {
		
		logger.debug("주문 save 확인 :: " + userId);
		
		// 사용자 아이디로 고객 정보 조회
		CustBas inCustEntity = CustBas.builder()
                .custId(userId)
                .build();
 
		List<CustBas> outCustBas = custRepository.findByDynamicQuery(inCustEntity.getCustId(), inCustEntity.getCustNm(), inCustEntity.getCustBirth(), inCustEntity.getCustEmail());
	 
		logger.debug("outCustBas ::: " + StringUtil.toString(outCustBas));
		
		CustBas outCustEntity = CustBas.builder()
		    .id(outCustBas.get(0).getId())
		    .custId(outCustBas.get(0).getCustId())
		    .custNm(outCustBas.get(0).getCustNm())
		    .custPassword(outCustBas.get(0).getCustPassword())
		    .custIdfyNo(outCustBas.get(0).getCustIdfyNo())
		    .custBirth(outCustBas.get(0).getCustBirth())
		    .custNo(outCustBas.get(0).getCustNo())
		    .custSex(outCustBas.get(0).getCustSex())
		    .custAddress(outCustBas.get(0).getCustAddress())
		    .custEmail(outCustBas.get(0).getCustEmail())
		    .intmPurStusYn(outCustBas.get(0).getIntmPurStusYn())
		    .build();
		
		// 임시 저장된 상품 모델 ID로 상품 조회
		IntmProductDTO intmProductDTO = IntmProductDTO.builder()
				.intmModelId(temporarySaveDTO.getTmpIntmModelId())
				.build();
		
		
		IntmProduct intmProduct = intmProductDTO.toEntity();
		
		List<IntmProduct> intmProductList = intmProductRepository.findModel(intmProduct.getIntmModelId());
		
		logger.debug("intmProductList ::: " + StringUtil.toString(intmProductList));
		
		IntmProduct outIntmProduct = null;
		
		logger.debug("intmProductList.size ::" + intmProductList.size());
		
		// 조회된 상품이 존재하는지 확인
		if(intmProductList.size() > 0) {
			// 각 상품의 판매 상태를 확인
			for(int i = 0; i < intmProductList.size(); i++) {
				// 현재 상품 판매 상태가 "N"인지 확인
				if(intmProductList.get(i).getIntmSalesStatus().equals("N")) {
					// 해당 상품을 outIntmProduct에 담기
					outIntmProduct = intmProductList.get(i);
					logger.debug("outIntmProduct if문 안쪽 ::: " + StringUtil.toString(outIntmProduct));
					
					// 선택된 상품을 사용
					break;
				}
			}
		}
		
		logger.debug("outIntmProduct ::: " + StringUtil.toString(outIntmProduct));
		
		// 주문 DTO 생성, 주문 저장
		OrdersDTO ordersDto = OrdersDTO.builder()
				.intmKorNm(ordersDTO.getIntmKorNm())
				.price(ordersDTO.getPrice())
				.orderUid(ordersDTO.getOrderUid())
				.custBas(outCustEntity)
				.intmProduct(outIntmProduct)
				.status(AllStatus.READY)
				.paymentUid(ordersDTO.getPaymentUid())
				.paymentDate(LocalDate.now())
				.build();
		
		Orders orders = ordersDto.toEntity();
    	
		logger.debug("orders :: " + StringUtil.toString(orders));
		
		orderRepository.save(orders);
		
		// 주문한 상품의 판매 상태 변경
		if (orders.getIntmProduct() != null) {
		    orders.getIntmProduct().setIntmSalesStatus("Y");
		    intmProductRepository.save(orders.getIntmProduct());
		    logger.debug("IntmSalesStatus 변경 완료");
		}
		
   }
	
	
	/**
	 * 주문내역 - 김시우
	 * 마이페이지
	 * 
	 * @param inDTO
	 * @return
	 */
	public List<OrdersDTO> getOrdersByCustBas(CustBasDTO inDTO) {
		
		logger.debug("주문내역 서비스");
		List<OrdersDTO> outList = new ArrayList<>();
		
		CustBas inCust = custService.getMember(inDTO).toEntity();
		
		logger.debug("inCust ::: " + inCust.getId());
		
		List<Orders> inOrders = orderRepository.findByCustBasId(inCust.getId());
		
		for(Orders outOrders : inOrders) {
			
			OrdersDTO outDTO = outOrders.toDTO();
			
			outList.add(outDTO);
		}
		
		return outList;
	}

}
