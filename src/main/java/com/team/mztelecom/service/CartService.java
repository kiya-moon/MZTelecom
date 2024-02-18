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

import lombok.RequiredArgsConstructor;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CartService {

	private static final Logger logger = LoggerFactory.getLogger(CartService.class);

	private final CustRepository custRepository;

	private final ProductRepository productRepository;

	private final CartRepository cartRepository;

	private final CartItemRepository cartItemRepository;
	
	/**
	 * 장바구니에 상품 담기 - 박지윤
	 * 
	 * @param cartItemDTO
	 * @param custId
	 * @return
	 */
	public Long addCart(CartItemDTO cartItemDTO, String custId){
		
		// 장바구니에 담을 상품 조회
		IntmBas item = productRepository.findById(cartItemDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        
        logger.debug("서비스 item ::: " + StringUtil.toString(item));
        
        // 로그인한 회원 조회
        Optional<CustBas> member = custRepository.findByCustId(custId);
        
        // 현재 로그인한 회원 장바구니 조회
        Cart cart = cartRepository.findByCustBasId(member.get().getId());
        
        logger.debug("서비스 custBas ::: " + member.get().getId());
        
        // 처음 장바구니 사용
        if (Utiles.isNullOrEmpty(cart)) {
			
        	CustBas custBas = member.get();
        	// 해당 회원의 장바구니 생성
        	cart = Cart.builder()
                    .custBas(custBas)
                    .build();

			cartRepository.save(cart);
			
		}
        
        // 현재 상품이 장바구니에 있는 상품인지 확인
        CartItem savedCartItem = cartItemRepository.findByCartIdAndIntmBasId(cart.getId(), item.getId());
        
        if(!Utiles.isNullOrEmpty(savedCartItem)){
        	logger.debug("savedCartItem if 확인");
            Long errorCode = 100L;
            
            return errorCode;
            
        } else {
        	
        	logger.debug("확인");
        	// 장바구니 상품 엔티티 생성
        	CartItem cartItem = CartItem.builder()
        		    .cart(cart)
        		    .intmBas(item)
        		    .build();
        	
            cartItemRepository.save(cartItem);
            
            return cartItem.getId();
        }
    }

	/**
	 * 장바구니 조회 - 박지윤
	 * 
	 * @param custId
	 * @return
	 */
	@Transactional
    public List<IntmBasDTO> getCart(String custId) {
    	
    	logger.debug("장바구니 조회 서비스");
    	
    	// 장바구니 상품 목록 저장 리스트 생성
    	List<IntmBasDTO> intmBasList = new ArrayList<>();
    	
    	// 고객 ID에 해당하는 회원 정보 가져오기
    	Optional<CustBas> member = custRepository.findByCustId(custId);
    	
    	
    	// 회원의 장바구니 정보
    	Cart cart = cartRepository.findByCustBasId(member.get().getId());
    	
    	// 장바구니가 비어있는 경우 빈 리스트를 반환
    	if (Utiles.isNullOrEmpty(cart)) { 
    		return intmBasList;
    	}
    	
    	// 장바구니에 담긴 상품
    	List<CartItem> cartItems = cartItemRepository.findCartItemsByCartId(cart.getId());
    	
    	logger.debug("장바구니 조회 cartItems ::: " + StringUtil.toString(cartItems));
        
    	for (CartItem cartItem : cartItems) {
    		Long cartItemId = cartItem.getId();
    		IntmBas intmBas = cartItem.getIntmBas();
            IntmBasDTO intmBasDTO = convertIntmBasDTO(intmBas);
            
            intmBasDTO.setCartItemId(cartItemId);
            
            intmBasList.add(intmBasDTO);
        }

    	logger.debug("장바구니 조회 intmBasList ::: " + StringUtil.toString(intmBasList));
    	
        return intmBasList;
        
    }
    
	/**
	 * 상품 convert - 박지윤
	 * 
	 * @param intmBas
	 * @return
	 */
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
    
    /**
     * 이미지 convert - 박지윤
     * 
     * @param intmImg
     * @return
     */
    private IntmImgDTO convertIntmImgDTO(IntmImg intmImg) {
        return IntmImgDTO.builder()
                .id(intmImg.getId())
                .intmNm(intmImg.getIntmNm())
                .imgName(intmImg.getImgName())
                .imgPath(intmImg.getImgPath())
                .build();
    }
    
    /**
     * 장바구니에 담긴 상품 삭제 - 박지윤
     * 
     * @param cartItemId
     */
    @Transactional
    public void deleteCartItem(Long cartItemId) {
    	
    	logger.debug("장바구니 delete 서비스");
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(EntityNotFoundException::new);
        
        cartItemRepository.delete(cartItem);
    }
    
    
}
