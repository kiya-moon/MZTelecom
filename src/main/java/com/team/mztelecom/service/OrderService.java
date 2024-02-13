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
	
	public void setIntmProduct() {
		
		String intmKorNm = temporarySaveDTO.getOrderTmp().get(0);
		String cliColor = temporarySaveDTO.getOrderTmp().get(1);
		String cliCpcty = temporarySaveDTO.getOrderTmp().get(2);
		
		String intmModelId="";
		
		if(intmKorNm.equals("갤럭시Z플립5")) {
			
			if(cliColor.equals("black")) {
				
				if(cliCpcty.equals("128GB")) {
					intmModelId = "K703031";	// 검정, 128 
				} else {
					intmModelId = "K703032"; 	// 검정 , 512
				}
				
			} else { // 민트
				if(cliCpcty.equals("128GB")) {
					intmModelId = "K703033";	// 민트, 128 
				} else {
					intmModelId = "K703034"; 	// 민트 , 512
				}
			}
			
		} else if(intmKorNm.equals("갤럭시S23_Ultra")) {
			if(cliColor.equals("black")) {
				if(cliCpcty.equals("128GB")) {
					intmModelId = "K703041";	// 검정, 128 
				} else {
					intmModelId = "K703042"; 	// 검정 , 512
				}
			} else {	// 그린
				if(cliCpcty.equals("128GB")) {
					intmModelId = "K703041";	// 그린, 128 
				} else {
					intmModelId = "K703042"; 	// 그린 , 512
				}
			}
		}
		logger.debug("intmModelId :: " + intmModelId);
		temporarySaveDTO.setTmpIntmModelId(intmModelId);
	}
	

	public void updateCustAddress(String address, Principal principal) {

		String userId = principal.getName();
		
		logger.debug("아이디 확인 :: " + userId);

		Optional<CustBas> custBas = custRepository.findByCustId(userId);

		if (!Utiles.isNullOrEmpty(custBas)) {
			
			CustBas custBasEntity = custBas.get();

			custBasEntity.UpdateAddress(address);

			custRepository.save(custBasEntity);
		}
	}
	
	// 주문 add
	public void ordersSave(String userId, OrdersDTO ordersDTO) {
		
		logger.debug("주문 save 확인 :: " + userId);
		
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
		
		
		IntmProductDTO intmProductDTO = IntmProductDTO.builder()
				.intmModelId(temporarySaveDTO.getTmpIntmModelId())
				.build();
		
		
		IntmProduct intmProduct = intmProductDTO.toEntity();
		
		List<IntmProduct> intmProductList = intmProductRepository.findModel(intmProduct.getIntmModelId());
		
		logger.debug("intmProductList ::: " + StringUtil.toString(intmProductList));
		
		IntmProduct outIntmProduct = null;
		
		logger.debug("intmProductList.size ::" + intmProductList.size());
		if(intmProductList.size() > 0) {
			for(int i = 0; i < intmProductList.size(); i++) {
				if(intmProductList.get(i).getIntmSalesStatus().equals("N")) {
					outIntmProduct = intmProductList.get(i);
					logger.debug("outIntmProduct if문 안쪽 ::: " + StringUtil.toString(outIntmProduct));
					
					break;
				}
			}
		}
		
		logger.debug("outIntmProduct ::: " + StringUtil.toString(outIntmProduct));
		
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
