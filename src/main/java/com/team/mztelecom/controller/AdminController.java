package com.team.mztelecom.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.team.mztelecom.domain.AllStatus;
import com.team.mztelecom.domain.IntmImg;
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.dto.IntmImgDTO;
import com.team.mztelecom.dto.QnADTO;
import com.team.mztelecom.service.AdminService;
import com.team.mztelecom.service.AttachmentService;
import com.team.mztelecom.service.ProductService;
import com.team.util.Utiles;

import ch.qos.logback.core.model.Model;

@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	AdminService adminService;

	@Autowired
	AttachmentService attachmentService;

	@Autowired
	ProductService productService;

	/**
	 * 문의내역 답변 완료 - 김시우
	 * 
	 * @param id
	 * @return
	 */
	@PostMapping(value = "/answrCmplt")
	public String answrQnA(@RequestParam("qna-id") Long id) {

		logger.debug("id :: " + id);

		QnADTO inDTO = QnADTO.builder().id(id).build();

		adminService.deleteQna(inDTO);

		return "redirect:/admin?tab=qna";
	}

	@PostMapping(value = "/productAdd")
	public String productAdd(@RequestParam("add-name") String addName, @RequestParam("add-kor") String addKor,
			@RequestParam("add-capacity") String addCapacity, @RequestParam("add-price") String addPrice,
			@RequestParam("add-color") String addColor, @RequestParam("add-image") MultipartFile addImage,
			@RequestParam("add-imageDetail") MultipartFile addImageDetail, Model model) {

		logger.debug("상품 등록 시작");
		logger.debug("addImageDetail : " + addImageDetail.getOriginalFilename());
		
		if (Utiles.isNullOrEmpty(addImage)) {
			addImage = null;
		}

		if (Utiles.isNullOrEmpty(addImageDetail)) {
			addImageDetail = null;
		}

		List<String> capacity = new ArrayList<>();
		List<String> price = new ArrayList<>();
		List<String> color = new ArrayList<>();

		// 기기용량
		String[] capacityList = addCapacity.split(",");

		if (!Utiles.isNullOrEmpty(capacityList)) {
			for (int i = 0; i < capacityList.length; i++) {
				capacity.add(capacityList[i]);
			}
		} else {
			capacity.add(addCapacity);
		}

		// 기기값
		String[] priceList = addPrice.split(",");

		if (!Utiles.isNullOrEmpty(priceList)) {
			for (int i = 0; i < priceList.length; i++) {
				price.add(priceList[i]);
			}
		} else {
			price.add(addPrice);
		}

		// 기기색상
		String[] colorList = addColor.split(",");

		if (!Utiles.isNullOrEmpty(colorList)) {
			for (int i = 0; i < colorList.length; i++) {
				color.add(colorList[i]);
			}
		} else {
			color.add(addColor);
		}

		List<IntmImgDTO> prodctImages = attachmentService.addImages(addImage, addImageDetail, addName); 
		
		IntmBasDTO intmBasAdd = IntmBasDTO.builder()
				.intmNm(addName)
				.intmModelColor(color)
				.intmKorNm(addKor)
				.intmGB(capacity)
				.intmPrice(price)
				.createdAt(LocalDate.now())
				.build();

		adminService.addProduct(intmBasAdd, prodctImages);

		logger.debug("상품" + addName);
		logger.debug("상품" + addKor);
		logger.debug("상품" + addCapacity);
		logger.debug("상품" + addPrice);
		logger.debug("상품" + addColor);

		return "redirect:/admin?tab=product";
	}

	@PostMapping("/update/{id}")
    public ResponseEntity<String> productUpdate(@RequestParam("id") Long productId,
            @RequestParam("productName") String productName,
            @RequestParam("productKorName") String productKorName,
            @RequestParam("productCapacity") List<String> productCapacity,
            @RequestParam("productPrice") List<String> productPrice,
            @RequestParam("productColor") List<String> productColor,
            @RequestParam(value = "productImage", required = false) MultipartFile productImage,
            @RequestParam(value = "productImageDetail", required = false) MultipartFile productImageDetail) {
        
		logger.debug("상품 수정 컨트롤러");
		
		try {
	        adminService.updateIntmBasDTO(productId, productName, productKorName, productCapacity, productPrice, productColor, productImage, productImageDetail);
	        return ResponseEntity.ok().body("{\"message\": \"상품 수정이 완료되었습니다.\"}");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"상품 수정 중 오류가 발생했습니다.\"}");
	    }
	}
	
	@DeleteMapping(value = "/delete-multiple")
	public ResponseEntity<String> deleteMultipleProducts(@RequestBody Map<String, List<Long>> requestBody) {
	    List<Long> ids = requestBody.get("ids");
	    if (ids == null) {
	        return ResponseEntity.badRequest().body("Missing 'ids' parameter");
	    }

	    adminService.deleteProduct(ids);

	    return ResponseEntity.ok("Selected products deleted successfully");
	}

	
	@GetMapping("/intmimg/{id}")
	@ResponseBody
	public Resource img(@PathVariable Long id, Model model) throws IOException {
		
		logger.debug("상품 이미지 url 컨트롤러");
		
		Optional<IntmImg> intmImgs = productService.findByIntmBas(id);
		
		return new UrlResource("file:" + intmImgs.get().getImgPath());
	}
	
	@GetMapping("/intmDetailimg/{id}")
	@ResponseBody
	public Resource imgDetail(@PathVariable Long id, Model model) throws IOException {
		
		logger.debug("상품 이미지 url 컨트롤러");
		
		Optional<IntmImg> intmImgs = productService.findByIntmBas(id);
		
		return new UrlResource("file:" + intmImgs.get().getImgDetailPath());
	}

	/**	
	 * 회원삭제 - 문기연
	 * @param custIdList
	 * @return
	 */
	@DeleteMapping("/admin/delete-cust")
	public ResponseEntity<String> deleteCustomers(@RequestBody List<Long> custIds) {
		logger.debug("회원삭제 컨트롤러");
		logger.debug("custIds :: " + custIds);
		
        try {
            adminService.deleteCust(custIds);
            return ResponseEntity.ok("선택한 회원이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("삭제에 실패했습니다. : " + e.getMessage());
        }
    }

	@PatchMapping("/admin/patch-order/{id}")
	public ResponseEntity<?> patchResource(@PathVariable Long id, @RequestParam("status") AllStatus status) throws Exception {
		
		logger.debug("주문현황 업데이트 컨트롤러");
		logger.debug("status :: " + status);
		
		adminService.updateOrderStatus(id, status);
		
		return ResponseEntity.ok().body("{\"message\": \"주문 상태가 업데이트되었습니다.\"}");
	}
}
