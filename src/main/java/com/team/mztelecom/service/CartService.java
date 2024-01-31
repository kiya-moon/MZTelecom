package com.team.mztelecom.service;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.team.mztelecom.domain.Cart;
import com.team.mztelecom.domain.CartItem;
import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.domain.IntmImg;
import com.team.mztelecom.dto.CartItemDTO;
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.dto.IntmImgDTO;
import com.team.mztelecom.repository.CartItemRepository;
import com.team.mztelecom.repository.CartRepository;
import com.team.mztelecom.repository.CustRepository;
import com.team.mztelecom.repository.ProductRepository;
import com.team.util.StringUtil;
import com.team.util.Utiles;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService {

	private static final Logger logger = LoggerFactory.getLogger(CartService.class);

	private final CustRepository custRepository;

	private final ProductRepository productRepository;

	private final CartRepository cartRepository;

	private final CartItemRepository cartItemRepository;
	
	private final CustService custService;
	
	// 장바구니 담기
//	public Long addCart(CartItemDTO cartItemDTO, String custId) {
//
//		IntmBas item = productRepository.findById(cartItemDTO.getId()).orElseThrow(EntityNotFoundException::new);
//		logger.debug("서비스 item ::: " + StringUtil.toString(item));
//		
//		CustBasDTO inDTO = CustBasDTO.builder()
//							.custId(custId)
//							.build();
//		
//		CustBasDTO outDTO = custService.getMember(inDTO);
//		
//		logger.debug("outDTO ::: " + StringUtil.toString(outDTO));
//		
//		Optional<CustBas> custBasOptional = custRepository.findByCustId(custId);
//		
//		logger.debug("서비스 custBasOptional ::: " + custBasOptional);
//
//		CustBas custBas = custBasOptional.orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다."));
//		
//		
//		CustBas custBas = outDTO.toEntity();
//
//		logger.debug("custBAs ::: " + custBas.getId());
//		
//		Cart cart = cartRepository.findByCustBasId(custBas.getId());
//
//		if (Utiles.isNullOrEmpty(cart)) {
//			logger.debug("서비스 cart if ::: " + StringUtil.toString(cart));
//			
//			cart = Cart.builder()
//					.custBas(custBas)
//					.build();
//
//			cartRepository.save(cart);
//			
//			cart = cartRepository.findByCustBasId(custBas.getId());
//		}
//
//		
//		
//		CartItem cartItem = cartItemRepository.findByCartIdAndIntmBasId(cart.getId(), item.getId());
//		
//		logger.debug("서비스 cartItem ::: " + StringUtil.toString(cartItem));
//
//		if (!Utiles.isNullOrEmpty(cartItem)) {
//			
//			logger.debug("서비스 if문 ::: " + StringUtil.toString(cartItem));
//			
//			cartItem.addCount(cartItemDTO.getCount());
//
//			return cartItem.getId();
//
//		} else {
//			
//			logger.debug("서비스 else ::: " + StringUtil.toString(cartItem));
//			
//			cartItem = CartItem.builder()
//					.cart(cart)
//					.intmBas(item)
//					.build();
//
//			cartItemRepository.save(cartItem);
//			
//			CartItem outCartItem = cartItemRepository.findByCartIdAndIntmBasId(cart.getId(), item.getId());
//			
//			return outCartItem.getId();
//		}
//	}
	
	public Long addCart(CartItemDTO cartItemDTO, String custId){
		
		// 장바구니에 담을 상품 조회
        IntmBas item = productRepository.findById(cartItemDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        
        logger.debug("서비스 item ::: " + StringUtil.toString(item));
        
        // 로그인한 회원 조회
        Optional<CustBas> member = custRepository.findByCustId(custId);
        
        CustBas custBas = member.orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다."));

        logger.debug("서비스 custBas ::: " + StringUtil.toString(custBas));
        
        // 현재 로그인한 회원 장바구니 조회
        Cart cart = cartRepository.findByCustBasId(custBas.getId());
        
        // 처음 장바구니 사용
        if (Utiles.isNullOrEmpty(cart)) {
			
        	// 해당 회원의 장바구니 생성
        	cart = Cart.builder()
                    .custBas(custBas)
                    .build();

			cartRepository.save(cart);
			
		}
        
        // 현재 상품이 장바구니에 이미 있는 상품인지 확인
        CartItem savedCartItem = cartItemRepository.findByCartIdAndIntmBasId(cart.getId(), item.getId());
        
        logger.debug("서비스 savedCartItem ::: " + StringUtil.toString(savedCartItem));
        
        if(!Utiles.isNullOrEmpty(savedCartItem)){
        	// 기존 수량에 현재 수량 더하기
            savedCartItem.addCount(cartItemDTO.getCount());
            return savedCartItem.getId();
            
        } else {
        	// 장바구니 상품 엔티티 생성
        	CartItem cartItem = CartItem.builder()
        		    .cart(cart)
        		    .intmBas(item)
        		    .count(cartItemDTO.getCount())
        		    .build();
        	
        	
        	logger.debug("서비스 cartItem ::: " + StringUtil.toString(cartItem));
			
            cartItemRepository.save(cartItem);
            
            return cartItem.getId();
        }
    }

	
	// 장바구니 조회
    public List<IntmBasDTO> getCart(String custId) {
    	
    	List<IntmBasDTO> intmBasList = new ArrayList<>();
    	
    	Optional<CustBas> custBasOptional = custRepository.findByCustId(custId);
    	
    	logger.debug("장바구니 조회 custBasOptional ::: " + StringUtil.toString(custBasOptional));
    	
    	CustBas custBas = custBasOptional.orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다."));
		
    	Cart cart = cartRepository.findByCustBasId(custBas.getId());
    	
    	logger.debug("장바구니 조회 cart ::: " + StringUtil.toString(cart));
    	
    	if (Utiles.isNullOrEmpty(cart)) { 
    		return intmBasList;
    	}
    	
    	List<CartItem> cartItems = cartItemRepository.findCartItemsByCartId(cart.getId());
    	
    	logger.debug("장바구니 조회 cartItems ::: " + StringUtil.toString(cartItems));
        
    	for (CartItem cartItem : cartItems) {
            IntmBas intmBas = cartItem.getIntmBas();
            IntmBasDTO intmBasDTO = convertIntmBasDTO(intmBas);
            
            intmBasList.add(intmBasDTO);
        }

    	logger.debug("장바구니 조회 intmBasList ::: " + StringUtil.toString(intmBasList));
    	
        return intmBasList;
        
    }
    
    // 상품
    private IntmBasDTO convertIntmBasDTO(IntmBas intmBas) {
    	
    	List<IntmImgDTO> intmImgDTOList = intmBas.getIntmImgs().stream()
                .map(this::convertIntmImgDTO)
                .collect(Collectors.toList());
    	
    	return IntmBasDTO.builder()
                .id(intmBas.getId())
                .intmModelColor(intmBas.getIntmModelColor())
                .intmNm(intmBas.getIntmNm())
                .intmKorNm(intmBas.getIntmKorNm())
                .intmGB(intmBas.getIntmGB())
                .intmPrice(intmBas.getIntmPrice())
                .wishCnt(intmBas.getWishCnt())
                .intmImgs(intmImgDTOList)
                .fee(intmBas.getFee())
                .build();
    	
    	
    }
    
    // 이미지
    private IntmImgDTO convertIntmImgDTO(IntmImg intmImg) {
        return IntmImgDTO.builder()
                .id(intmImg.getId())
                .intmNm(intmImg.getIntmNm())
                .imgName(intmImg.getImgName())
                .imgPath(intmImg.getImgPath())
                .build();
    }
    
    
}
