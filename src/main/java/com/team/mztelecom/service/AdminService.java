package com.team.mztelecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.team.mztelecom.domain.AllStatus;
import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.domain.IntmImg;
import com.team.mztelecom.domain.Orders;
import com.team.mztelecom.domain.QnA;
import com.team.mztelecom.dto.InquiryCustDTO;
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.dto.IntmImgDTO;
import com.team.mztelecom.dto.OrdersDTO;
import com.team.mztelecom.dto.QnADTO;
import com.team.mztelecom.repository.AdminRepository;
import com.team.mztelecom.repository.ImgRepository;
import com.team.mztelecom.repository.OrderRepository;
import com.team.mztelecom.repository.ProductRepository;
import com.team.mztelecom.repository.QnARepository;
import com.team.util.StringUtil;
import com.team.util.Utiles;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {
	private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
	
	private final AdminRepository adminRepository;
	private final QnARepository qnARepository;
	private final ProductRepository productRepository;
	private final ImgRepository imgRepository;
	private final AttachmentService attachmentService;
	private final OrderRepository orderRepository;

	// 1. 사용자 정보 담기
	public List<InquiryCustDTO> getCustInfoList() {
		logger.debug("Admin 서비스");

		List<CustBas> custBasList = adminRepository.findAllCustInfo();
		List<InquiryCustDTO> inquiryCustDTO = new ArrayList<>();

		for (CustBas custBas : custBasList) {
			logger.debug("inquiryCustDTO :: " + StringUtil.toString(inquiryCustDTO));

			InquiryCustDTO result = new InquiryCustDTO(custBas.getId(), custBas.getCustId(), custBas.getCustNm(),
					custBas.getCustBirth(), custBas.getCustNo(), custBas.getCustSex(),
					custBas.getCustAddress(), custBas.getCustEmail(), custBas.getIntmPurStusYn(),
					custBas.getCreateDate());

			inquiryCustDTO.add(result);
		}

		logger.debug("inquiryCustDTO :: " + StringUtil.toString(inquiryCustDTO));

		return inquiryCustDTO;
	}

	public Page<InquiryCustDTO> getCustInfoPage(String keyword, Pageable pageable) {
		if (StringUtils.hasText(keyword)) {
			return adminRepository.findByCustNmContaining(keyword, pageable).map(this::mapToDTO);
		} else {
			return adminRepository.findAll(pageable).map(this::mapToDTO);
		}
	}

	private InquiryCustDTO mapToDTO(CustBas custBas) {
		return InquiryCustDTO.builder().id(custBas.getId()).custId(custBas.getCustId()).custNm(custBas.getCustNm()).custBirth(custBas.getCustBirth())
				.custNo(custBas.getCustNo()).custSex(custBas.getCustSex()).custAddress(custBas.getCustAddress())
				.custEmail(custBas.getCustEmail()).intmPurStusYn(custBas.getIntmPurStusYn()).createDate(custBas.getCreateDate()).build();
	}

	// 2-1. 상품 정보 담기
	@Transactional
	public void addProduct(IntmBasDTO intmBasDTO, List<IntmImgDTO> inList) {
		logger.debug("상품 담기 서비스 :: ");
		
		// IntmBas 엔터티 생성
	    IntmBas intmBasAdd = IntmBas.builder()
	            .intmModelColor(intmBasDTO.getIntmModelColor())
	            .intmNm(intmBasDTO.getIntmNm())
	            .intmKorNm(intmBasDTO.getIntmKorNm())
	            .intmGB(intmBasDTO.getIntmGB())
	            .intmPrice(intmBasDTO.getIntmPrice())
	            .fee(intmBasDTO.getFee())
	            .createdAt(intmBasDTO.getCreatedAt())
	            .build();

	    productRepository.save(intmBasAdd);

	    for (IntmImgDTO intmImgDTO : inList) {
	        IntmImg intmImgAdd = IntmImg.builder()
	                .intmNm(intmImgDTO.getIntmNm())
	                .imgName(intmImgDTO.getImgName())
	                .imgDetailNm(intmImgDTO.getImgDetailNm())
	                .imgPath(intmImgDTO.getImgPath())
	                .imgDetailPath(intmImgDTO.getImgDetailPath())
	                .intmBas(intmBasAdd) 
	                .build();

	        imgRepository.save(intmImgAdd);
	    }
	}
	
	// 2-2 상품 수정
	@Transactional
	public void updateIntmBasDTO(Long id, String productName, String productKorName, List<String> productCapacity,
	        List<String> productPrice, List<String> productColor, MultipartFile productImage,
	        MultipartFile productImageDetail) {

	    logger.debug("상품 수정 서비스 :: ");
	    
	    Optional<IntmBas> intmBasOptional = productRepository.findById(id);
	    
	    if (!Utiles.isNullOrEmpty(productImage) && !Utiles.isNullOrEmpty(productImageDetail)) {
	        // 상품 이미지가 업로드되었다면
	        IntmImg savedImgs = imgRepository.findByIntmBasId(id).orElseThrow(EntityNotFoundException::new);

	        logger.debug("수정 savedImgs :: " + StringUtil.toString(savedImgs));

	        if (!Utiles.isNullOrEmpty(savedImgs.getImgName())) {
	        	attachmentService.deleteFile(savedImgs.getImgPath());
	        }
	        
	        if (!Utiles.isNullOrEmpty(savedImgs.getImgDetailNm())) {
	        	attachmentService.deleteFile(savedImgs.getImgDetailPath());
	        }

	        // 상품 이미지 업로드 및 저장
			List<IntmImgDTO> uploadedImagesAll = attachmentService.addImages(productImage, productImageDetail, productName);
			
			logger.debug("상품 수정 uploadedImages :: " + StringUtil.toString(uploadedImagesAll));
			
			for (IntmImgDTO imgDTO : uploadedImagesAll) {
		        savedImgs.updateImage(
		        		imgDTO.getIntmNm(), 
		        		imgDTO.getImgName(), 
		        		imgDTO.getImgPath(), 
		        		imgDTO.getImgDetailNm(),
		                imgDTO.getImgDetailPath());
		        
		            imgRepository.save(savedImgs);
	        }

	    } else if (!Utiles.isNullOrEmpty(productImage)) {
	        // 상품 이미지가 업로드되었다면
	        IntmImg savedImg = imgRepository.findByIntmBasId(id).orElseThrow(EntityNotFoundException::new);

	        logger.debug("수정 savedImg :: " + StringUtil.toString(savedImg));

	        if (!Utiles.isNullOrEmpty(savedImg.getImgName())) {
	        	attachmentService.deleteFile(savedImg.getImgPath());
	        }

	        // 상품 이미지 업로드 및 저장
			List<IntmImgDTO> uploadedImages = attachmentService.addImages(productImage, null, productName);
			
			for (IntmImgDTO imgDTO : uploadedImages) {
				savedImg.updateImg(
		        		imgDTO.getIntmNm(), 
		        		imgDTO.getImgName(), 
		        		imgDTO.getImgPath()
		        );
		        
		        imgRepository.save(savedImg);
		    }
			
			
	    } else if (!Utiles.isNullOrEmpty(productImageDetail)) {
	        // 상품 상세 이미지가 업로드되었다면
	        IntmImg savedImgDetail = imgRepository.findByIntmBasId(id).orElseThrow(EntityNotFoundException::new);

	        logger.debug("수정 savedImgDetail :: " + StringUtil.toString(savedImgDetail));

	        if (!Utiles.isNullOrEmpty(savedImgDetail.getImgName())) {
	        	attachmentService.deleteFile(savedImgDetail.getImgDetailPath());
	        }

	        // 상품 상세 이미지 업로드 및 저장
	        List<IntmImgDTO> uploadedImagesDetail = attachmentService.addImages(null, productImageDetail, productName);
			
	        for (IntmImgDTO imgDTO : uploadedImagesDetail) {
	            savedImgDetail.updateImgDetail(
	            		imgDTO.getIntmNm(), 
	            		imgDTO.getImgDetailNm(), 
	            		imgDTO.getImgDetailPath()
	            );
	            
	            imgRepository.save(savedImgDetail);
	        }
	    }
	    
	    // 기존 상품 정보 업데이트
	    IntmBas intmBasGet = intmBasOptional.get();
	    intmBasGet.updateProduct(id, productName, productKorName, productCapacity, productPrice, productColor);
	    
	    productRepository.save(intmBasGet);
	}

	
	// 2-3 상품 삭제
	@Transactional
	public void deleteProduct(List<Long> ids) {
		for (Long id : ids) {
			
			IntmImg savedImgs = imgRepository.findByIntmBasId(id).orElseThrow(EntityNotFoundException::new);
			
			logger.debug("수정 savedImgs :: " + StringUtil.toString(savedImgs));
			
			if (!Utiles.isNullOrEmpty(savedImgs.getImgName())) {
				attachmentService.deleteFile(savedImgs.getImgPath());
			}
			
			if (!Utiles.isNullOrEmpty(savedImgs.getImgDetailNm())) {
				attachmentService.deleteFile(savedImgs.getImgDetailPath());
			}
			
            productRepository.deleteById(id);
        }
	}
	
	// 3. 문의 정보 담기
	/**
	 * 관리자 페이지 문의 내역 답변 완료 - 김시우
	 * 이메일로 답변 완료한 문의 내역 삭제.
	 * 
	 * @param inQnADTO
	 */
	public void deleteQna(QnADTO inQnADTO) {
		
		QnA inQnA = inQnADTO.toEntity();
		
		Optional<QnA> outQnA = qnARepository.findById(inQnA.getId());
		logger.debug("outQnA :: " + StringUtil.toString(outQnA.get()));
		
		qnARepository.deleteById(outQnA.get().getId());
		
	}
	
	// 4. 판매 정보 담기
	@Transactional
	public List<OrdersDTO> getOrdersList() {
		logger.debug("상품 담기 서비스 :: ");
		
		List<Orders> ordersList = orderRepository.findAll();
		List<OrdersDTO> ordersDTO = new ArrayList<>();
		
		for (Orders orders : ordersList) {
			logger.debug("ordersDTO :: " + StringUtil.toString(ordersDTO));

			OrdersDTO result = new OrdersDTO(orders.getId(), orders.getIntmKorNm(), orders.getPrice(), orders.getOrderUid(), orders.getCustBas(), orders.getIntmProduct(), orders.getStatus(), orders.getPaymentUid(), orders.getPaymentDate());

			ordersDTO.add(result);
		}

		logger.debug("ordersDTO :: " + StringUtil.toString(ordersDTO));

		return ordersDTO;
	}

	@PersistenceContext
	private EntityManager entityManager;
	
	// 5. 회원삭제
	public void deleteCust(List<Long> custIds) {
		logger.debug("회원삭제 서비스 도착 :: " + custIds);
		
		
		for (Long id : custIds) {
			logger.debug("for문");
			CustBas custBas = new CustBas(id);
	        try {
	            orderRepository.deleteByCustBas(custBas);
	            // 삭제 성공 메시지를 로깅하거나 반환할 수 있음
	            System.out.println("Orders deleted successfully for custBasId: " + id);
	        } catch (Exception e) {
	            // 예외가 발생하면 예외 정보를 로깅하고, 예외 메시지를 출력하거나 적절한 처리를 수행할 수 있음
	            System.err.println("Error deleting orders for custBasId: " + id);
	            e.printStackTrace();
	            // 예외를 다시 던져도 됨
	            throw e;
	        }
			logger.debug("orders 삭제 완료");
            adminRepository.deleteById(id);
            logger.debug("custBas 삭제 완료");
        }	
	}
	
	// 6. 주문현황 업데이트
	@Transactional
	public void updateOrderStatus(Long id, AllStatus status) throws Exception {
		logger.debug("주문현황 업데이트 서비스");
		
		if (AllStatus.READY.equals(status)) {
			status = AllStatus.IN_PROGRESS;
		} else if (AllStatus.IN_PROGRESS.equals(status)) {
			status = AllStatus.COMPLETED;
		}
		
		int updatedEntities = entityManager.createQuery("UPDATE Orders o SET o.status = :status WHERE o.id = :id")
                .setParameter("status", status)
                .setParameter("id", id)
                .executeUpdate();
        if (updatedEntities == 0) {
            throw new Exception("주문을 찾을 수 없습니다.");
        }
	}

	public Page<OrdersDTO> getOrdersPage(Pageable ordersPage) {
		
		return orderRepository.findAll(ordersPage).map(this::mapToDTO);
	}

	private OrdersDTO mapToDTO(Orders orders) {
		return OrdersDTO.builder()
				.id(orders.getId())
				.intmKorNm(orders.getIntmKorNm())
				.price(orders.getPrice())
                .orderUid(orders.getOrderUid())
                .custBas(orders.getCustBas())
                .intmProduct(orders.getIntmProduct())
                .status(orders.getStatus())
                .paymentUid(orders.getPaymentUid())
                .paymentDate(orders.getPaymentDate())
                .build();
	}
}
